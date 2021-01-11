package callum.project.uni.rms.service;

import callum.project.uni.rms.model.req.AppCreateReq;
import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.service.model.response.application.ApplicationsForUser;
import callum.project.uni.rms.service.repository.RoleApplicationRepository;
import callum.project.uni.rms.service.repository.model.ApplicationStatus;
import callum.project.uni.rms.service.repository.model.RoleApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleApplicationServiceTest {

    private RoleApplicationService roleApplicationService;

    private RoleApplicationRepository roleApplicationRepository;
    private static final Long USER_ID = 1L;


    @BeforeEach
    void setup() {
        roleApplicationRepository = mock(RoleApplicationRepository.class);
        roleApplicationService = new RoleApplicationService(roleApplicationRepository);
    }

    @Test
    @DisplayName("Test happy path, should send all params to db")
    void addNewApplication_happyPath() throws ServiceException {

        RoleApplication roleApplication = RoleApplication.builder()
                .accountId("123")
                .applicantId(1L)
                .projectId("2")
                .roleId("3")
                .applicationDate(Date.valueOf(LocalDate.now()))
                .lastUpdatedDate(Date.valueOf(LocalDate.now()))
                .applicationStatus(ApplicationStatus.SUBMITTED)
                .applicationId(null)
                .build();

        when(roleApplicationRepository.save(eq(roleApplication)))
                .thenReturn(new RoleApplication());

        AppCreateReq createReq = AppCreateReq.builder()
                .accountNumber("123")
                .applicantId("1")
                .projectCode("2")
                .roleId("3")
                .build();

        roleApplicationService.addNewApplication(createReq);
    }

    @Test
    @DisplayName("Test empty response from role application retrieve all apps")
    void retrieveAllApplicationsForUser_emptyList() throws ServiceException {
        when(roleApplicationRepository.findAllByApplicantId(eq(USER_ID)))
                .thenReturn(emptyList());
        ApplicationsForUser response = roleApplicationService.retrieveAllApplicationsForUser(USER_ID);
        assertTrue(response.getApplicationInfoList().isEmpty());
    }
}
