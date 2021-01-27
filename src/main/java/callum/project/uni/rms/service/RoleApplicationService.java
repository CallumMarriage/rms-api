package callum.project.uni.rms.service;

import callum.project.uni.Application;
import callum.project.uni.rms.model.req.AppCreateReq;
import callum.project.uni.rms.model.res.application.HasUserAlreadyApplied;
import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.service.mapper.ApplicationMapper;
import callum.project.uni.rms.model.res.application.ApplicationsForUser;
import callum.project.uni.rms.model.res.application.NumOfApps;
import callum.project.uni.rms.model.res.TargetApplication;
import callum.project.uni.rms.service.repository.RoleApplicationRepository;
import callum.project.uni.rms.service.repository.model.ApplicationStatus;
import callum.project.uni.rms.service.repository.model.RoleApplication;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class RoleApplicationService {

    private final RoleApplicationRepository roleApplicationRepository;

    /**
     * Retrieve particular application based on it's id. Only returns info pertaining to the application itself.
     *
     * @param applicationId application id
     * @return application info in target format.
     */
    public TargetApplication retrieveApplicationById(Long applicationId) throws ServiceException {
        try {
            Optional<RoleApplication> roleApplication = roleApplicationRepository.findById(applicationId);
            if (roleApplication.isEmpty()) {
                throw new ServiceException("Application not found");
            }
            return ApplicationMapper.mapAppToTargetApp(roleApplication.get());
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Retrieve all of the applications for a user.
     *
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

        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ServiceException("Issue calling retrieve applications for user");
        }
    }

    public ApplicationsForUser retrieveApplicationsForRm(Long rmId) throws ServiceException {

        try {
            List<RoleApplication> applications = roleApplicationRepository.findAllByResourceManager(rmId);
            return ApplicationsForUser.builder()
                    .applicationInfoList(
                            applications.stream()
                                    .map(ApplicationMapper::mapAppToTargetApp)
                                    .collect(Collectors.toList()))
                    .build();

        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ServiceException("Issue calling retrieve applications for rm");
        }
    }

    public NumOfApps retrieveNumOfApplicationsForUser(Long userId) throws ServiceException {

        try {
            int numOfApps = roleApplicationRepository.countDistinctByApplicantId(userId);
            return NumOfApps.builder()
                    .numOfApplications(numOfApps)
                    .build();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    public void confirmApplication(Long applicationId) throws ServiceException {
        try {
            log.info("Confirming the following application: " + applicationId);

            roleApplicationRepository.updateApplicationToBeConfirmed(applicationId, createCurrentDate());
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    public void rejectApplication(Long applicationId) throws ServiceException {
        try {
            log.info("Rejecting the following application: " + applicationId);
            roleApplicationRepository.updateApplicationToBeRejected(applicationId, createCurrentDate());
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    public void markApplicationAsInReview(Long applicationId) throws ServiceException {
        try {
            log.info("Rejecting the following application: " + applicationId);
            roleApplicationRepository.updateApplicationToInReview(applicationId, createCurrentDate());
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
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ServiceException("Issue creating application for user");
        }
    }

    public HasUserAlreadyApplied userAlreadyApplied(Long roleId, Long userId) throws ServiceException {
        try{
            Optional<RoleApplication> hasApp = roleApplicationRepository
                    .findFirstByRoleIdAndApplicantId(roleId, userId);

            return HasUserAlreadyApplied.builder()
                    .userAlreadyApplied(hasApp.isPresent())
                    .build();
        } catch (RuntimeException e){
            log.error(e.getMessage());
            throw new ServiceException("Issue checking if user has already made application");
        }
    }

    private Date createCurrentDate() {
        return Date.valueOf(LocalDate.now());
    }
}
