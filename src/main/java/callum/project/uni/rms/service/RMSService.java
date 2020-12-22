package callum.project.uni.rms.service;

import callum.project.uni.rms.service.mapper.AccountMapper;
import callum.project.uni.rms.service.mapper.RoleMapper;
import callum.project.uni.rms.service.model.TargetAccount;
import callum.project.uni.rms.service.model.TargetRole;
import callum.project.uni.rms.service.repository.AccountRepository;
import callum.project.uni.rms.service.repository.RoleRepository;
import callum.project.uni.rms.service.repository.model.Account;
import callum.project.uni.rms.service.repository.model.Role;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class RMSService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    private final AccountMapper accountMapper;
    private final RoleMapper roleMapper;

    public TargetAccount retrieveTargetAccount(String accountId){
        Optional<Account> accountOptional = accountRepository.findById(accountId);

        if(accountOptional.isEmpty()){
            return null;
        }
        
        Account account = accountOptional.get();
        return accountMapper.mapAccountToTargetAccount(account);
    }

    public TargetRole retrieveTargetRole(String roleId){
        log.info("REQUEST - ROLE ID: ?", roleId);
        Optional<Role> roleOptional = roleRepository.findById(roleId);
        log.info("RECIEVED REQUEST");
        if(roleOptional.isEmpty()){
            return null;
        }

        Role role = roleOptional.get();
        return roleMapper.mapDynamoDBToTargetModel(role);
    }
}
