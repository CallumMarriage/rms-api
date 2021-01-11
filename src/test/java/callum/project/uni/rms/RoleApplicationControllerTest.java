package callum.project.uni.rms;

import callum.project.uni.rms.model.req.AppCreateReq;
import callum.project.uni.rms.model.res.ControllerRes;
import callum.project.uni.rms.service.RoleApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class RoleApplicationControllerTest {


    private ApplicationController applicationController;
    private RoleApplicationService roleApplicationService;

    @BeforeEach
    void setup(){
        roleApplicationService = mock(RoleApplicationService.class);
        applicationController = new ApplicationController(roleApplicationService);
    }
    
    @Test
    void addUser_happyPath() {
        AppCreateReq req = AppCreateReq.builder()
                .accountNumber("1901901")
                .applicantId("1")
                .projectCode("128102")
                .roleId("2")
                .build();
        ResponseEntity<ControllerRes> res = applicationController.addNewApp(req);
        assertEquals(201, res.getStatusCode().value());
    }

    @ParameterizedTest
    @MethodSource("createAppCreateReqWithNullValues")
    @DisplayName("Test Add user when request values are null")
    void addUser_requestValuesNull(AppCreateReq req){

        ResponseEntity<ControllerRes> res = applicationController.addNewApp(req);
        assertEquals(400, res.getStatusCode().value());
    }

    private static Stream<AppCreateReq> createAppCreateReqWithNullValues(){
        return Stream.of(
                AppCreateReq.builder()
                        .projectCode("80181032")
                        .roleId("821312")
                        .applicantId("1")
                        .build(),
                AppCreateReq.builder()
                        .accountNumber("80318302")
                        .projectCode("80181032")
                        .applicantId("1")
                        .build(),
                AppCreateReq.builder()
                        .accountNumber("80318302")
                        .roleId("80181032")
                        .applicantId("1")
                        .build(),
                AppCreateReq.builder()
                        .accountNumber("80318302")
                        .projectCode("80181032")
                        .roleId("80181032")
                        .build()
        );
    }

}
