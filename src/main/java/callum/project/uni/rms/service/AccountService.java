package callum.project.uni.rms.service;

import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.service.mapper.AccountMapper;
import callum.project.uni.rms.service.mapper.RoleMapper;
import callum.project.uni.rms.service.model.response.AbstractServiceResponse;
import callum.project.uni.rms.service.model.response.TargetAccount;
import callum.project.uni.rms.service.model.response.TargetRole;
import callum.project.uni.rms.service.model.response.accounts.AccountList;
import callum.project.uni.rms.service.repository.AccountRepository;
import callum.project.uni.rms.service.repository.model.Account;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static callum.project.uni.rms.service.mapper.AccountMapper.mapAccountToTargetAccount;

@Service
@Slf4j
@AllArgsConstructor
public class AccountService {


    private final AccountRepository accountRepository;

    public TargetAccount getTargetAccountById(String accountId) throws ServiceException {
        try {
            Optional<Account> accountOptional = accountRepository.findById(accountId);
            if (accountOptional.isEmpty()) {
                return null;
            }

            return mapAccountToTargetAccount(accountOptional.get());
        } catch (RuntimeException e) {
            throw new ServiceException("Issue retrieving account");
        } catch (Exception e) {
            throw new ServiceException("Issue mapping account");
        }
    }

    public AccountList getAccountList() throws ServiceException {
        try {
            Iterable<Account> accountsIterable = accountRepository.findAll();

            List<TargetAccount> accountList = createList(accountsIterable);

            return AccountList.builder()
                    .accountList(accountList)
                    .build();
        } catch (RuntimeException e) {
            throw new ServiceException("Issue retrieving account");
        } catch (Exception e) {
            throw new ServiceException("Issue mapping account");
        }
    }

    public AccountList getAccountList(String accountName) throws ServiceException {
        try {
            List<Account> accountsList = accountRepository.findAllByAccountName(accountName);
            List<TargetAccount> mappedAccounts = accountsList.parallelStream()
                    .map(AccountMapper::mapAccountToTargetAccount)
                    .collect(Collectors.toList());

            return AccountList.builder()
                    .accountList(mappedAccounts).build();
        } catch (RuntimeException e) {
            throw new ServiceException("Issue retrieving account");
        } catch (Exception e) {
            throw new ServiceException("Issue mapping account");
        }
    }


    private List<TargetAccount> createList(Iterable<Account> accountIterable) {
        return StreamSupport.stream(accountIterable.spliterator(), true)
                .map(AccountMapper::mapAccountToTargetAccount)
                .collect(Collectors.toList());
    }
}
