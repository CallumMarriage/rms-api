package callum.project.uni.rms.service;

import callum.project.uni.rms.common.RoleType;
import callum.project.uni.rms.model.req.RequestRole;
import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.service.mapper.RoleMapper;
import callum.project.uni.rms.model.res.TargetRole;
import callum.project.uni.rms.model.res.TargetRoleHistory;
import callum.project.uni.rms.model.res.role.PotentialRoles;
import callum.project.uni.rms.service.repository.RoleRepository;
import callum.project.uni.rms.service.repository.model.Role;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public PotentialRoles retrieveNewRoleRequests(Long rmId) throws ServiceException {
//        try {
////            List<Role> roles = roleRepository.findAllByActiveIsFalse();
//
//            return mapListToPotentialRoles(roles);
//        } catch (RuntimeException e){
//            log.error(e.getMessage());
//            throw new ServiceException("Trouble retrieving role requests");
//        }
        return null;
    }

    public PotentialRoles runFilters(String accountName, String projectName, RoleType roleType) throws ServiceException {

        try {
            List<Role> potentialRoles = roleRepository.findPotentialRoles();
            if (accountName != null) {
                potentialRoles = potentialRoles.stream()
                        .filter(role -> accountName.equals(role.getAccountName())).collect(Collectors.toList());
            }
            if (projectName != null) {
                potentialRoles = potentialRoles.stream()
                        .filter(role -> projectName.equals(role.getProjectName())).collect(Collectors.toList());
            }
            if (roleType != null) {
                potentialRoles = potentialRoles.stream()
                        .filter(role -> roleType == role.getRoleType()).collect(Collectors.toList());
            }
            return mapListToPotentialRoles(potentialRoles);
        } catch (RuntimeException e) {
            throw new ServiceException("Error running the filter");
        }
    }


    public Role addNewRole(RequestRole requestRole) throws ServiceException {

        try {
            Role role = RoleMapper.mapRequestToDbModel(requestRole);
            return roleRepository.save(role);
        } catch (RuntimeException e) {
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

    public List<TargetRole> retrieveAllRolesForProjectCode(String projectCode) throws ServiceException {
        try {
            List<Role> roles = roleRepository.findAllByProjectCode(projectCode);
            return createList(roles);

        } catch (HibernateException e) {
            throw new ServiceException("Error retrieving role");
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

    public void updateRoleAsActive(Long roleId) throws ServiceException {
        try {
            roleRepository.updateRoleOpen(roleId, false);

        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    private PotentialRoles mapListToPotentialRoles(List<Role> roles) {
        List<TargetRole> targetPotentialRoles = roles.stream()
                .map(RoleMapper::mapDynamoDBToTargetModel)
                .collect(Collectors.toList());

        return PotentialRoles.builder()
                .potentialRoles(targetPotentialRoles)
                .build();
    }

    public TargetRoleHistory retrieveRoleHistory(Long userId) throws ServiceException {
        //Collect all of the role ids associated to user
        try {
            Iterable<Role> roleIds = roleRepository.findRolesForUser(userId);
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
