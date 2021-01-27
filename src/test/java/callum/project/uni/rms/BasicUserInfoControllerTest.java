package callum.project.uni.rms;

import callum.project.uni.rms.common.UserType;
import callum.project.uni.rms.model.req.UserCreateReq;
import callum.project.uni.rms.model.res.*;
import callum.project.uni.rms.service.AccountService;
import callum.project.uni.rms.service.ProjectService;
import callum.project.uni.rms.service.RoleService;
import callum.project.uni.rms.service.UserService;
import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.model.res.user.FullUserInfo;
import callum.project.uni.rms.validator.RequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class BasicUserInfoControllerTest {

    private BasicUserInfoController basicUserInfoController;

    private UserService userService;

    private AccountService accountService;

    private ProjectService projectService;

    private RoleService roleService;

    private static final String SSO_ID = "2801830283028310810281";
    private static final Long USER_ID = 1L;
    private static final String PROJECT_CODE = "888";
    private static final String ACCOUNT_NUMBER = "999";
    private static final Long ROLE_ID = 1L;

    @BeforeEach
    void setup(){
        userService = mock(UserService.class);
        accountService = mock(AccountService.class);
        projectService = mock(ProjectService.class);
        roleService = mock(RoleService.class);
        basicUserInfoController = new BasicUserInfoController(
                new RequestValidator(),
                userService,
                roleService,
                accountService,
                projectService);
    }

    @Test
    @DisplayName("Test get user info happy path")
    void getUserInfo_happyPath() throws ServiceException {
        initMock();

        TargetUser mockedUser = TargetUser
                .builder()
                .id(USER_ID)
                .userType(UserType.CANDIDATE)
                .currentRoleId(ROLE_ID)
                .build();
        when(userService.retrieveUserDetailsBySsoId(eq(SSO_ID)))
                .thenReturn(mockedUser);

        ResponseEntity<AbstractServiceResponse> result = basicUserInfoController.getUserInfo(SSO_ID);
        assertEquals(200, result.getStatusCode().value());
        FullUserInfo responseBody = (FullUserInfo) result.getBody();
        validateFullInfoResponseBody(responseBody);
    }

    @Test
    @DisplayName("Test get user info bad request")
    void getUserInfo_badRequest(){
        ResponseEntity<AbstractServiceResponse> result = basicUserInfoController.getUserInfo(null);
        assertEquals(400, result.getStatusCode().value());
    }

    @Test
    @DisplayName("Test get User info server error thrown")
    void getUserInfo_serverError() throws ServiceException {
        when(userService.retrieveUserDetailsBySsoId(eq(SSO_ID)))
                .thenThrow(new ServiceException("TEST ERROR"));
        ResponseEntity<AbstractServiceResponse> result = basicUserInfoController.getUserInfo(SSO_ID);
        assertEquals(500, result.getStatusCode().value());
    }

    @Test
    @DisplayName("Test add user happy path")
    void addUser_happyPath() throws ServiceException {
        initMock();

        TargetUser mockedUser = TargetUser
                .builder()
                .id(USER_ID)
                .currentRoleId(ROLE_ID)
                .build();
        when(userService.createUser(any(UserCreateReq.class)))
                .thenReturn(mockedUser);


        UserCreateReq req = buildCreateReq();

        ResponseEntity<AbstractServiceResponse> result = basicUserInfoController.addNewUser(req);

        assertEquals(201, result.getStatusCode().value());
        FullUserInfo responseBody = (FullUserInfo) result.getBody();
        validateFullInfoResponseBody(responseBody);
    }

    @Test
    @DisplayName("Test add user bad request")
    void addUser_badReq(){
        UserCreateReq userCreateReq = UserCreateReq.builder()
                .build();
        ResponseEntity<AbstractServiceResponse> result = basicUserInfoController.addNewUser(userCreateReq);
        assertEquals(400, result.getStatusCode().value());
    }

    @Test
    @DisplayName("Test add user server error")
    void addUser_serverError() throws ServiceException {
        UserCreateReq userCreateReq = buildCreateReq();

        when(userService.createUser(any(UserCreateReq.class)))
                .thenThrow(new ServiceException("TEST ERROR"));
        ResponseEntity<AbstractServiceResponse> result = basicUserInfoController.addNewUser(userCreateReq);
        assertEquals(500, result.getStatusCode().value());
    }

    private void initMock() throws ServiceException {

        TargetRole mockedRole = TargetRole.builder()
                .roleName("TEST ROLE")
                .accountNumber(ACCOUNT_NUMBER)
                .projectCode(PROJECT_CODE)
                .id(ROLE_ID)
                .startDate(LocalDate.now())
                .build();

        when(roleService.retrieveTargetRole(eq(ROLE_ID)))
                .thenReturn(Optional.of(mockedRole));

        TargetAccount mockedAccount = TargetAccount.builder()
                .accountCode(ACCOUNT_NUMBER)
                .startDate(LocalDate.now())
                .build();

        when(accountService.getTargetAccountById(eq(ACCOUNT_NUMBER)))
                .thenReturn(mockedAccount);

        TargetProject mockedProject = TargetProject.builder()
                .projectCode(PROJECT_CODE)
                .build();

        when(projectService.getProjectById(eq(PROJECT_CODE)))
                .thenReturn(mockedProject);
    }

    private void validateFullInfoResponseBody(FullUserInfo responseBody){
        TargetUser user = responseBody.getUser();
        assertEquals(USER_ID, user.getId());
        assertEquals(ROLE_ID, user.getCurrentRoleId());
        TargetRole currentRole = responseBody.getCurrentRole();
        assertEquals(ROLE_ID, currentRole.getId());
        assertEquals(ACCOUNT_NUMBER, currentRole.getAccountNumber());
        assertEquals(PROJECT_CODE, currentRole.getProjectCode());
        TargetAccount currentAccount = responseBody.getCurrentAccount();
        assertEquals(ACCOUNT_NUMBER, currentAccount.getAccountCode());
        TargetProject currentProject = responseBody.getCurrentProject();
        assertEquals(PROJECT_CODE, currentProject.getProjectCode());
    }

    private UserCreateReq buildCreateReq(){
        return UserCreateReq.builder()
                .ssoId(SSO_ID)
                .userType(UserType.CANDIDATE)
                .build();
    }
}
