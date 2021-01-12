package callum.project.uni.rms.service;

import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.service.model.response.TargetAccount;
import callum.project.uni.rms.service.model.response.accounts.AccountList;
import callum.project.uni.rms.service.repository.AccountRepository;
import callum.project.uni.rms.service.repository.model.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    private AccountService accountService;

    private AccountRepository accountRepository;

    private static final String ACCOUNT_ID = "1234567";

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        accountService = new AccountService(accountRepository);
    }

    @Test
    @DisplayName("Test account service get accounts happy path")
    void getAccountsList_happyPath() throws ServiceException {
        LocalDate localStartDate = LocalDate.now();
        LocalDate localEndDate = LocalDate.now();
        Date startDate = Date.valueOf(localStartDate);
        Date endDate = Date.valueOf(localEndDate);

        Account account = new Account();
        account.setAccountName("Account");
        account.setAccountNumber("1234567");
        account.setDescription("Description");
        account.setStartDate(startDate);
        account.setEndDate(endDate);

        Iterable<Account> accountsIterable = List.of(account);

        when(accountRepository.findAll()).thenReturn(accountsIterable);

        AccountList accountList = accountService.getAccountList();
        assertEquals(1, accountList.getAccountList().size());
        TargetAccount targetAccount = accountList.getAccountList().get(0);
        assertEquals("1234567", targetAccount.getAccountCode());
        assertEquals("Description", targetAccount.getDescription());
        assertEquals(localStartDate, targetAccount.getStartDate());
        assertEquals(localEndDate, targetAccount.getEndDate());

    }

    @AfterEach
    void tearDown() {
    }
}