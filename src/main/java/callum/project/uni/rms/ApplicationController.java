package callum.project.uni.rms;

import callum.project.uni.rms.model.req.AppCreateReq;
import callum.project.uni.rms.model.res.ControllerRes;
import callum.project.uni.rms.service.RoleApplicationService;
import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.service.model.response.application.ApplicationsForUser;
import callum.project.uni.rms.service.model.response.application.NumOfApps;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static callum.project.uni.rms.ResponseBuilder.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
@AllArgsConstructor
public class ApplicationController {

    private final RoleApplicationService roleApplicationService;

    @GetMapping("/applications")
    public ResponseEntity<ControllerRes> retrieveAssignmentsForUser(@RequestParam String userId){
       try {
           if(userId == null || userId.isEmpty()){
               return buildBadReqResponse();
           }

           ApplicationsForUser applicationsForUser = roleApplicationService.retrieveAllApplicationsForUser(Long.parseLong(userId));
           if (applicationsForUser.getApplicationInfoList().isEmpty()) {
               return buildNotFoundResponse();
           }
           
           return buildOkResponse(applicationsForUser);
       } catch (ServiceException e){
           return buildErrorResponse();
       }
    }

    @GetMapping("/applications/amount")
    public ResponseEntity<ControllerRes> retrieveNumberOfApplications(@RequestParam String userId){
        try {
            NumOfApps numOfApps = roleApplicationService.retrieveNumOfApplicationsForUser(Long.parseLong(userId));

            return buildOkResponse(numOfApps);
        } catch (ServiceException e){
            return buildErrorResponse();
        }
    }

    @PostMapping("/application")
    public ResponseEntity<ControllerRes> addNewApp(@RequestBody AppCreateReq request){
        try {
            if(invalid(request.getAccountNumber()) ||
                    invalid(request.getProjectCode()) ||
                    invalid(request.getRoleId()) ||
                    invalid(request.getApplicantId())){
                return buildBadReqResponse();
            }

            log.info(request.getApplicantId());
            roleApplicationService.addNewApplication(request);

            return ResponseEntity.status(201).build();

        } catch (ServiceException e){
            return buildErrorResponse();
        }
    }

    private boolean invalid(String bodyValue){
        return bodyValue == null || bodyValue.isEmpty();
    }
}
