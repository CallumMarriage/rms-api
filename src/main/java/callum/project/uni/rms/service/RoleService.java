package callum.project.uni.rms.service;

import callum.project.uni.rms.common.RoleType;
import callum.project.uni.rms.model.req.RequestRole;
import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.service.mapper.RoleMapper;
import callum.project.uni.rms.service.model.response.role.PotentialRoles;
import callum.project.uni.rms.service.model.response.TargetRole;
import callum.project.uni.rms.service.model.response.TargetRoleHistory;
import callum.project.uni.rms.service.repository.RoleRepository;
import callum.project.uni.rms.service.repository.model.Assignment;
import callum.project.uni.rms.service.repository.model.Role;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
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


    public Role addNewRole(RequestRole requestRole) throws ServiceException {

        try{
            Role role = RoleMapper.mapRequestToDbModel(requestRole);
            log.info(role.toString());
            return roleRepository.save(role);
        } catch (RuntimeException e){
            log.error(e.getMessage());
            throw new ServiceException("Issue adding new role");
        }
    }

    public Optional<TargetRole> retrieveTargetRole(Long roleId) throws ServiceException {
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
    
    public List<TargetRole> retrieveForProjectId(String projectCode) throws ServiceException {
        try {
            List<Role> roles = roleRepository.findPotentialRolesByProjectCode(projectCode);
            return createList(roles);

        } catch (HibernateException e) {
            log.error(e.getLocalizedMessage());
            throw new ServiceException("Error retrieving role");
        }
    }

    public PotentialRoles retrievePotentialRolesByRoleType(RoleType roleType) throws ServiceException {
        List<Role> potentialRoles = roleRepository.findAllByRoleType(roleType);
        return mapListToPotentialRoles(potentialRoles);

    }

    public PotentialRoles retrievePotentialRoles() throws ServiceException {
        try {
            List<Role> potentialRoles = roleRepository.findPotentialRoles();

            return mapListToPotentialRoles(potentialRoles);

        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    private PotentialRoles mapListToPotentialRoles(List<Role> roles) {
        List<TargetRole> targetPotentialRoles = roles.stream()
                .map(RoleMapper::mapDynamoDBToTargetModel)
                .collect(Collectors.toList());

        return new PotentialRoles(targetPotentialRoles);
    }

    public TargetRoleHistory retrieveRoleHistory(Long userId) throws ServiceException {
        //Collect all of the role ids associated to user
        try {
            Iterable<Role> roleIds = roleRepository.findRolesForUser(userId);
            log.info(roleIds.toString());
            return TargetRoleHistory.builder()
                    .rolehistory(createList(roleIds))
                    .build();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
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
