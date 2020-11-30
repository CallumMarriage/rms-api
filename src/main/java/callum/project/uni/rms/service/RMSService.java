package callum.project.uni.rms.service;

import callum.project.uni.rms.service.mapper.AccountMapper;
import callum.project.uni.rms.service.model.TargetAccount;
import callum.project.uni.rms.service.repository.AccountRepository;
import callum.project.uni.rms.service.repository.model.Account;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class RMSService {

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    public TargetAccount retrieveTargetAccount(String accountId){
        Optional<Account> accountOptional = accountRepository.findById(accountId);

        if(accountOptional.isEmpty()){
            return null;
        }
        
        Account account = accountOptional.get();
        return accountMapper.mapAccountToTargetAccount(account);
    }
}
