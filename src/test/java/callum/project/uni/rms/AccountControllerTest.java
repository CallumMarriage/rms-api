package callum.project.uni.rms;

import callum.project.uni.rms.model.res.ControllerRes;
import callum.project.uni.rms.service.AccountService;
import callum.project.uni.rms.service.ProjectService;
import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.service.model.response.TargetAccount;
import callum.project.uni.rms.service.model.response.TargetProject;
import callum.project.uni.rms.service.model.response.accounts.FullAccountInfo;
import callum.project.uni.rms.validator.RequestValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    private AccountController accountController;

    private AccountService accountService;

    private ProjectService projectService;

    private static final String USER_ID = "1";

    @BeforeEach
    void setUp() {
        accountService = mock(AccountService.class);
        projectService = mock(ProjectService.class);
        RequestValidator requestValidator = new RequestValidator();
        accountController = new AccountController(accountService, projectService, requestValidator);
    }

    @Test
    @DisplayName("Happy path test")
    void retrieveTargetAccount_happyPath() throws ServiceException {
        TargetAccount mockedAccount = TargetAccount.builder()
                .accountCode("131232")
                .build();

        List<TargetProject> mockedProjects = singletonList(
                TargetProject.builder()
                        .projectCode("1")
                        .accountNumber("131232")
                        .projectName("project")
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now().plusDays(1))
                        .build()
        );
        when(accountService.getTargetAccountById(eq(USER_ID)))
                .thenReturn(mockedAccount);
        when(projectService.getProjectListForAccount("131232"))
                .thenReturn(mockedProjects);
        ResponseEntity<ControllerRes> result = accountController.retrieveTargetAccount(USER_ID);

        assertEquals(200, result.getStatusCode().value());
        FullAccountInfo accountInfo = (FullAccountInfo) result.getBody().getResponseBody();

        assertEquals("131232", accountInfo.getAccount().getAccountCode());
        assertEquals("1", accountInfo.getProjectList().get(0).getProjectCode());
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