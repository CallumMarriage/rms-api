package callum.project.uni.rms.service;

import callum.project.uni.rms.model.req.AppCreateReq;
import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.service.mapper.ApplicationMapper;
import callum.project.uni.rms.service.model.response.application.ApplicationsForUser;
import callum.project.uni.rms.service.model.response.application.NumOfApps;
import callum.project.uni.rms.service.model.response.TargetApplication;
import callum.project.uni.rms.service.repository.RoleApplicationRepository;
import callum.project.uni.rms.service.repository.model.ApplicationStatus;
import callum.project.uni.rms.service.repository.model.RoleApplication;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class RoleApplicationService {

    private final RoleApplicationRepository roleApplicationRepository;

    /**
     * Retrieve particular application based on it's id. Only returns info pertaining to the application itself.
     * @param applicationId application id
     * @return application info in target format.
     */
    public TargetApplication retrieveApplicationById(String applicationId){
        return null;
    }

    /**
     * Retrieve all of the applications for a user.
     * @param userId the internal user id.
     * @return the basic info for all of the applications a user has.
     */
    public ApplicationsForUser retrieveAllApplicationsForUser(Long userId) throws ServiceException {

        try {
            List<RoleApplication> applications = roleApplicationRepository.findAllByApplicantId(userId);
            return ApplicationsForUser.builder()
                    .applicationInfoList(
                            applications.stream()
                                    .map(ApplicationMapper::mapAppToTargetApp)
                                    .collect(Collectors.toList()))
                    .build();

        } catch (RuntimeException e){
            log.error(e.getMessage());
            throw new ServiceException("Issue calling retrieve applications for user");
        }

    }

    public NumOfApps retrieveNumOfApplicationsForUser(Long userId) throws ServiceException {

        try {
            int numOfApps =  roleApplicationRepository.countDistinctByApplicantId(userId);
            return NumOfApps.builder()
                    .numOfApplications(numOfApps)
                    .build();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    public void addNewApplication(AppCreateReq request) throws ServiceException {
        try {
            RoleApplication application = RoleApplication.builder()
                    .applicationDate(Date.valueOf(LocalDate.now()))
                    .applicationStatus(ApplicationStatus.SUBMITTED)
                    .accountId(request.getAccountNumber())
                    .projectId(request.getProjectCode())
                    .applicantId(request.getApplicantId())
                    .lastUpdatedDate(Date.valueOf(LocalDate.now()))
                    .roleId(request.getRoleId())
                    .build();
            
            roleApplicationRepository.save(application);
        } catch (RuntimeException e){
            log.error(e.getMessage());
            throw new ServiceException("Issue creating application for user");
        }
    }
}
