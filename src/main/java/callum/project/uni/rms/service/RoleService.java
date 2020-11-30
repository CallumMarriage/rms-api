package callum.project.uni.rms.service;

import callum.project.uni.rms.service.mapper.RoleMapper;
import callum.project.uni.rms.service.model.TargetRole;
import callum.project.uni.rms.service.repository.RoleRepository;
import callum.project.uni.rms.service.repository.model.Role;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class RoleService {

//    private final DynamoRepository repository;

    private final RoleMapper roleMapper;

    private final RoleRepository roleRepository;

    public TargetRole getRole(String id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
//        Optional<Role> roleOptional = repository.findRoleById(id, roleName);
//        if (optionalRole.isEmpty()) {
//            throw new ServiceException("Not found");
//        }
        Role role = optionalRole.get();

        return roleMapper.mapDynamoDBToTargetModel(role);
    }
}
