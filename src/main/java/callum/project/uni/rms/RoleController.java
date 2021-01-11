package callum.project.uni.rms;

import callum.project.uni.rms.model.res.ControllerRes;
import callum.project.uni.rms.service.RoleService;
import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.service.model.response.TargetRole;
import callum.project.uni.rms.service.model.response.TargetRoleHistory;
import callum.project.uni.rms.service.model.response.role.PotentialRoles;
import callum.project.uni.rms.validator.RequestValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static callum.project.uni.rms.ResponseBuilder.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
@AllArgsConstructor
public class RoleController {

    private final RequestValidator requestValidator;
    private final RoleService roleService;

    @GetMapping(value = "/roles")
    public ResponseEntity<ControllerRes> getRoleHistory(@RequestParam String userId) {
        log.info("REQUEST RECEIVED");
        if(userId == null || userId.isEmpty()){
            log.debug("REQUEST: Failed validation");
            return buildBadReqResponse();
        }

        try {
            TargetRoleHistory serviceResponse = roleService.retrieveRoleHistory(Long.parseLong(userId));

            if (serviceResponse.getRolehistory().isEmpty()) {
                log.debug("REQUEST: Not found");

                return buildNotFoundResponse();
            }

            return buildOkResponse(serviceResponse);

        } catch (ServiceException e) {
            log.debug("REQUEST: System Error during processing");

            return buildErrorResponse();
        }
    }

    @GetMapping(value = "/role/id/{id}", produces = "application/json")
    public ResponseEntity<ControllerRes> getTargetRole(@PathVariable String id) {
        log.info("REQUEST RECEIVED");

        if (requestValidator.validateGetByIdReq(id)) {
            log.debug("REQUEST: Failed validation");
            return buildBadReqResponse();
        }

        try {
            Optional<TargetRole> serviceResponse = roleService.retrieveTargetRole(id);

            if (serviceResponse.isEmpty()) {
                log.debug("REQUEST: Not found");

                return buildNotFoundResponse();
            }

            log.debug("REQUEST: OK");
            return buildOkResponse(serviceResponse.get());

        } catch (ServiceException e) {
            log.debug("REQUEST: System Error during processing");

            return buildErrorResponse();
        }
    }

    @GetMapping("/potentialRoles")
    public ResponseEntity<ControllerRes> potentialRoles() {
        try {
            PotentialRoles response = roleService.retrievePotentialRoles();

            if (response == null) {
                return buildNotFoundResponse();
            }

            return buildOkResponse(response);

        } catch (ServiceException e) {
            return buildErrorResponse();
        }
    }
}
