package callum.project.uni.rms.service;

import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.service.model.response.AbstractServiceResponse;
import callum.project.uni.rms.service.model.response.TargetAccount;
import callum.project.uni.rms.service.repository.AccountRepository;
import callum.project.uni.rms.service.repository.model.Account;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static callum.project.uni.rms.service.mapper.AccountMapper.mapAccountToTargetAccount;

@Service
@Slf4j
@AllArgsConstructor
public class AccountService {


    private final AccountRepository accountRepository;

    public TargetAccount getTargetAccountById(String accountId) throws ServiceException{
        try {
            Optional<Account> accountOptional = accountRepository.findById(accountId);
            if (accountOptional.isEmpty()) {
                return null;
            }

            return mapAccountToTargetAccount(accountOptional.get());
        } catch (RuntimeException e){
            throw new ServiceException("Issue retrieving account");
        } catch (Exception e){
            throw new ServiceException("Issue mapping account");
        }
    }
}
