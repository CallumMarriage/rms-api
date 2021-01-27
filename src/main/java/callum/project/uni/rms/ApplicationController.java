package callum.project.uni.rms;

import callum.project.uni.rms.model.req.AppCreateReq;
import callum.project.uni.rms.model.req.RoleAppUpdateStatusReq;
import callum.project.uni.rms.model.res.AbstractServiceResponse;
import callum.project.uni.rms.model.res.TargetApplication;
import callum.project.uni.rms.model.res.TargetRole;
import callum.project.uni.rms.model.res.application.ApplicationSubmitted;
import callum.project.uni.rms.model.res.application.ApplicationsForUser;
import callum.project.uni.rms.model.res.application.HasUserAlreadyApplied;
import callum.project.uni.rms.model.res.application.NumOfApps;
import callum.project.uni.rms.service.*;
import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.validator.RequestValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

import static callum.project.uni.rms.ResponseBuilder.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
@AllArgsConstructor
public class ApplicationController {

    private final RoleApplicationService roleApplicationService;
    private final RequestValidator requestValidator;
    private final AuthorizerService authorizerService;
    private final UserService userService;
    private final AssignmentService assignmentService;
    private final RoleService roleService;

    @GetMapping("/applications")
    public ResponseEntity<AbstractServiceResponse> retrieveAssignmentsForUser(@RequestParam Long userId) {
        try {
            if (requestValidator.invalid(userId)) {
                return buildBadReqResponse();
            }

            ApplicationsForUser applicationsForUser = roleApplicationService.retrieveAllApplicationsForUser(userId);
            if (applicationsForUser.getApplicationInfoList().isEmpty()) {
                return buildNotFoundResponse();
            }

            return buildOkResponse(applicationsForUser);
        } catch (ServiceException e) {
            return buildErrorResponse();
        }
    }

    @GetMapping(value = "/applications", params = "rmId")
    public ResponseEntity<AbstractServiceResponse> retrieveApplicationsForRm(@RequestParam Long rmId) {
        try {
            if (requestValidator.invalid(rmId)) {
                return buildBadReqResponse();
            }

            ApplicationsForUser applicationsForUser = roleApplicationService.retrieveApplicationsForRm(rmId);

            if (applicationsForUser.getApplicationInfoList().isEmpty()) {
                return buildNotFoundResponse();
            }

            return buildOkResponse(applicationsForUser);
        } catch (ServiceException e) {
            return buildErrorResponse();
        }
    }

    @GetMapping("/userAlreadyApplied")
    public ResponseEntity<AbstractServiceResponse> userAlreadyApplied(@RequestParam Long userId, @RequestParam Long roleId) {
        if (requestValidator.invalid(userId) || requestValidator.invalid(roleId)) {
            return buildBadReqResponse();
        }

        try {
            HasUserAlreadyApplied res = roleApplicationService.userAlreadyApplied(roleId, userId);

            return buildOkResponse(res);
        } catch (ServiceException e) {
            return buildErrorResponse();
        }
    }

    @GetMapping("/applications/amount")
    public ResponseEntity<AbstractServiceResponse> retrieveNumberOfApplications(@RequestParam String userId) {
        try {
            NumOfApps numOfApps = roleApplicationService.retrieveNumOfApplicationsForUser(Long.parseLong(userId));

            return buildOkResponse(numOfApps);
        } catch (ServiceException e) {
            return buildErrorResponse();
        }
    }

    @PostMapping("/application/confirm")
    public ResponseEntity<AbstractServiceResponse> confirmApp(@RequestBody RoleAppUpdateStatusReq req) {
        try {
            if (requestValidator.invalid(req.getApplicationId()) || requestValidator.invalid(req.getAuthorizerId())) {
                log.error("Bad request");
                return buildBadReqResponse();
            }
            TargetApplication roleApplication = roleApplicationService.retrieveApplicationById(req.getApplicationId());

            if (authorizerService.userAuthorizedToConfirmApplication(req.getAuthorizerId(),
                    roleApplication.getApplicantId())) {
                log.info("User has passed authorization");
                Optional<TargetRole> roleOpt = roleService.retrieveTargetRole(roleApplication.getRoleId());

                if (roleOpt.isEmpty()) {
                    log.error("Error as role used in application does not exist.");
                    return buildErrorResponse();
                }

                roleApplicationService.confirmApplication(req.getApplicationId());

                userService.updateUserProjectDetails(roleApplication.getApplicantId(), roleApplication.getRoleId());
                TargetRole role = roleOpt.get();

                roleService.updateRoleAsActive(role.getId());

                LocalDate startDate = role.getStartDate();
                LocalDate endDate = role.getEndDate();

                assignmentService.addNewAssignment(roleApplication.getApplicantId(), roleApplication.getRoleId(),
                        startDate, endDate);

                return ResponseEntity.status(201)
                        .body(ApplicationSubmitted.builder()
                                .appSubmitted(true)
                                .build());
            }

            return ResponseEntity.status(202)
                    .body(ApplicationSubmitted.builder()
                            .appSubmitted(false).build());

        } catch (ServiceException e) {
            return buildErrorResponse();
        }
    }

    @PostMapping("/application/reject")
    public ResponseEntity<AbstractServiceResponse> rejectApp(@RequestBody RoleAppUpdateStatusReq req) {
        try {
            if (requestValidator.invalid(req.getApplicationId()) || requestValidator.invalid(req.getAuthorizerId())) {
                return buildBadReqResponse();
            }
            TargetApplication roleApplication = roleApplicationService.retrieveApplicationById(req.getApplicationId());

            if (authorizerService.userAuthorizedToConfirmApplication(req.getAuthorizerId(), roleApplication.getApplicantId())) {

                Optional<TargetRole> roleOpt = roleService.retrieveTargetRole(roleApplication.getRoleId());

                if (roleOpt.isEmpty()) {
                    log.error("Error as role used in application does not exist.");
                    return buildErrorResponse();
                }

                roleApplicationService.rejectApplication(req.getApplicationId());

                return ResponseEntity.status(201)
                        .body(ApplicationSubmitted.builder()
                                .appSubmitted(true)
                                .build());
            }

            return ResponseEntity.status(202)
                    .body(ApplicationSubmitted.builder()
                            .appSubmitted(false).build());

        } catch (ServiceException e) {
            return buildErrorResponse();
        }
    }

    @PostMapping("/application/inReview")
    public ResponseEntity<AbstractServiceResponse> markAsInReview(@RequestBody RoleAppUpdateStatusReq req) {
        try {
            if (requestValidator.invalid(req.getApplicationId()) || requestValidator.invalid(req.getAuthorizerId())) {
                return buildBadReqResponse();
            }
            TargetApplication roleApplication = roleApplicationService.retrieveApplicationById(req.getApplicationId());

            if (authorizerService.userAuthorizedToConfirmApplication(req.getAuthorizerId(), roleApplication.getApplicantId())) {

                Optional<TargetRole> roleOpt = roleService.retrieveTargetRole(roleApplication.getRoleId());

                if (roleOpt.isEmpty()) {
                    log.error("Error as role used in application does not exist.");
                    return buildErrorResponse();
                }

                roleApplicationService.markApplicationAsInReview(req.getApplicationId());

                return ResponseEntity.status(201)
                        .body(ApplicationSubmitted.builder()
                                .appSubmitted(true)
                                .build());
            }

            return ResponseEntity.status(202)
                    .body(ApplicationSubmitted.builder()
                            .appSubmitted(false)
                            .build());

        } catch (ServiceException e) {
            return buildErrorResponse();
        }
    }


    @PostMapping("/application/submit")
    public ResponseEntity<AbstractServiceResponse> addNewApp(@RequestBody AppCreateReq request) {
        try {
            if (requestValidator.invalid(request.getAccountNumber()) ||
                    requestValidator.invalid(request.getProjectCode()) ||
                    requestValidator.invalid(request.getRoleId()) ||
                    requestValidator.invalid(request.getApplicantId())) {
                log.error("Bad request");
                return buildBadReqResponse();
            }

            Optional<TargetRole> roleOpt = roleService.retrieveTargetRole(request.getRoleId());

            if (roleOpt.isEmpty()) {
                log.error("Error as role used in application does not exist.");
                return buildErrorResponse();
            }

            roleApplicationService.addNewApplication(request);

            return ResponseEntity.status(201)
                    .body(TargetApplication.builder().build());

        } catch (ServiceException e) {
            return buildErrorResponse();
        }
    }
}
