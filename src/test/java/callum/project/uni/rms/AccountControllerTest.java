package callum.project.uni.rms;

import callum.project.uni.rms.model.res.ControllerRes;
import callum.project.uni.rms.service.AccountService;
import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.service.model.response.TargetAccount;
import callum.project.uni.rms.validator.RequestValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    private AccountController accountController;

    private AccountService accountService;

    private static final String USER_ID = "1";

    @BeforeEach
    void setUp() {
        accountService = mock(AccountService.class);
        RequestValidator requestValidator = new RequestValidator();
        accountController = new AccountController(accountService, requestValidator);
    }

    @Test
    @DisplayName("Happy path test")
    void retrieveTargetAccount_happyPath() throws ServiceException {
        TargetAccount mockedAccount = TargetAccount.builder()
                .accountCode("131232")
                .build();
        when(accountService.getTargetAccountById(eq(USER_ID)))
                .thenReturn(mockedAccount);
        ResponseEntity<ControllerRes> result = accountController.retrieveTargetAccount(USER_ID);

        assertEquals(200, result.getStatusCode().value());
        TargetAccount targetAccount = (TargetAccount) result.getBody().getResponseBody();

        assertEquals("131232", targetAccount.getAccountCode());
    }

    @Test
    @DisplayName("Bad request")
    void retrieveTargetAccount_badRequest() {
        ResponseEntity<ControllerRes> result = accountController.retrieveTargetAccount(null);

        assertEquals(400, result.getStatusCode().value());
    }


    @Test
    @DisplayName("Service returns error")
    void retrieveTargetAccount_serviceException() throws ServiceException {
        when(accountService.getTargetAccountById(eq(USER_ID)))
                .thenThrow(new ServiceException("TEST - Could not connect"));
        ResponseEntity<ControllerRes> result = accountController.retrieveTargetAccount(USER_ID);

        assertEquals(500, result.getStatusCode().value());
    }
}