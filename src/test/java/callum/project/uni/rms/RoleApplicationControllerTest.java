package callum.project.uni.rms;

import callum.project.uni.rms.model.req.AppCreateReq;
import callum.project.uni.rms.model.res.AbstractServiceResponse;
import callum.project.uni.rms.model.res.TargetAccount;
import callum.project.uni.rms.model.res.TargetRole;
import callum.project.uni.rms.service.*;
import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.validator.RequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleApplicationControllerTest {


    private ApplicationController applicationController;

    private static final Long USER_ID = 1L;
    private static final Long ROLE_ID = 2L;
    private RoleApplicationService roleApplicationService;
    private RoleService roleService;
    private AuthorizerService authorizerService;
    private AssignmentService assignmentService;
    private UserService userService;

    @BeforeEach
    void setup() {
        roleApplicationService = mock(RoleApplicationService.class);
        authorizerService = mock(AuthorizerService.class);
        roleService = mock(RoleService.class);
        assignmentService = mock(AssignmentService.class);
        userService = mock(UserService.class);
        applicationController = new ApplicationController(
                roleApplicationService, new RequestValidator(),
                authorizerService, userService,
                assignmentService, roleService);
    }

    @Test
    void addUser_happyPath() throws ServiceException {
        AppCreateReq req = AppCreateReq.builder()
                .accountNumber("1901901")
                .applicantId(USER_ID)
                .projectCode("128102")
                .roleId(ROLE_ID)
                .build();
        Optional<TargetRole> targetRoleOptional = Optional.of(TargetRole.builder()
                .id(ROLE_ID)
                .build());

        when(roleService.retrieveTargetRole(ROLE_ID)).thenReturn(targetRoleOptional);
        ResponseEntity<AbstractServiceResponse> res = applicationController.addNewApp(req);
        assertEquals(201, res.getStatusCode().value());
    }

    @ParameterizedTest
    @MethodSource("createAppCreateReqWithNullValues")
    @DisplayName("Test Add user when request values are null")
    void addUser_requestValuesNull(AppCreateReq req) {

        ResponseEntity<AbstractServiceResponse> res = applicationController.addNewApp(req);
        assertEquals(400, res.getStatusCode().value());
    }

    private static Stream<AppCreateReq> createAppCreateReqWithNullValues() {
        return Stream.of(
                AppCreateReq.builder()
                        .projectCode("80181032")
                        .roleId(ROLE_ID)
                        .applicantId(USER_ID)
                        .build(),
                AppCreateReq.builder()
                        .accountNumber("80318302")
                        .projectCode("80181032")
                        .applicantId(USER_ID)
                        .build(),
                AppCreateReq.builder()
                        .accountNumber("80318302")
                        .roleId(ROLE_ID)
                        .applicantId(USER_ID)
                        .build(),
                AppCreateReq.builder()
                        .accountNumber("80318302")
                        .projectCode("80181032")
                        .roleId(ROLE_ID)
                        .build()
        );
    }

}
