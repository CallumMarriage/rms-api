package callum.project.uni.rms.service;

import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.service.mapper.RoleMapper;
import callum.project.uni.rms.service.model.response.AbstractServiceResponse;
import callum.project.uni.rms.service.model.response.TargetRole;
import callum.project.uni.rms.service.model.response.TargetRoleHistory;
import callum.project.uni.rms.service.repository.RoleRepository;
import callum.project.uni.rms.service.repository.model.Role;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Optional<TargetRole> retrieveTargetRole(String roleId) throws ServiceException {
        Optional<Role> roleOptional;
        try {
            roleOptional = roleRepository.findById(roleId);
        } catch (HibernateException e) {
            log.error(e.getLocalizedMessage());
            throw new ServiceException("Error retrieving role");
        }
        if (roleOptional.isEmpty()) return Optional.empty();

        try {
            return Optional.of(RoleMapper.mapDynamoDBToTargetModel(roleOptional.get()));
        } catch (Exception e) {
            throw new ServiceException("Error building response object");
        }
    }

    public TargetRoleHistory retrieveRoleHistory(int userId) throws ServiceException {
        //Collect all of the role ids associated to user
        try {
            Iterable<Role> roleIds = roleRepository.findRolesForUser(userId);

            return TargetRoleHistory.builder()
                    .rolehistory(createList(roleIds))
                    .build();
        } catch (RuntimeException e) {
            log.info(e.getMessage());
            throw new ServiceException(e.getMessage());
        }

    }

    private List<TargetRole> createList(Iterable<Role> roleIterable) {
        return StreamSupport.stream(roleIterable.spliterator(), true)
                .map(RoleMapper::mapDynamoDBToTargetModel)
                .sorted(Comparator.comparing(TargetRole::getStartDate,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());
    }
}
