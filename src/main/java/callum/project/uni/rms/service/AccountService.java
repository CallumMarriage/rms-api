package callum.project.uni.rms.service;

import callum.project.uni.rms.service.mapper.AccountMapper;
import callum.project.uni.rms.service.model.TargetAccount;
import callum.project.uni.rms.service.repository.AccountRepository;
import callum.project.uni.rms.service.repository.model.Account;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AccountService {

    private final AccountMapper accountMapper;

    private final AccountRepository accountRepository;

    public TargetAccount getTargetAccountById(String accountId){
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isEmpty()){
            return null;
        }

        return accountMapper.mapAccountToTargetAccount(accountOptional.get());
    }
}
