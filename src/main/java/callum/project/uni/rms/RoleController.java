package callum.project.uni.rms;

import callum.project.uni.rms.common.RoleType;
import callum.project.uni.rms.model.req.RequestRole;
import callum.project.uni.rms.model.req.RoleCreateReq;
import callum.project.uni.rms.model.res.AbstractServiceResponse;
import callum.project.uni.rms.service.AssignmentService;
import callum.project.uni.rms.service.RoleService;
import callum.project.uni.rms.service.UserService;
import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.model.res.TargetRole;
import callum.project.uni.rms.model.res.TargetRoleHistory;
import callum.project.uni.rms.model.res.role.PotentialRoles;
import callum.project.uni.rms.service.repository.model.Role;
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
    private final UserService userService;
    private final AssignmentService assignmentService;

    @GetMapping(value = "/roles")
    public ResponseEntity<AbstractServiceResponse> filterPotentialRoles(
            @RequestParam(required = false) String accountName,
            @RequestParam(required = false) String projectName,
            @RequestParam(required = false) RoleType roleType) {

        try {
            PotentialRoles potentialRoles = roleService.runFilters(accountName, projectName, roleType);
            return buildOkResponse(potentialRoles);
        } catch (ServiceException e){
            return buildErrorResponse();
        }
    }

    @PostMapping(value = "/role")
    public ResponseEntity<AbstractServiceResponse> postRoleWithUser(@RequestBody RoleCreateReq req) {
        log.info("REQUEST RECEIVED");
        Long userId = req.getUserId();
        RequestRole requestRole = req.getRole();

        log.info(requestRole.toString());
        try {
            // Add role to role database
            Role createdRole = roleService.addNewRole(requestRole);

            // Add role with user to assignment
            assignmentService.addNewAssignment(userId,
                    createdRole.getId(),
                    requestRole.getStartDate(),
                    requestRole.getEndDate());

            //If no end date update current user current role / current account /current project
            if (requestRole.getEndDate() == null) {
                userService.updateUserProjectDetails(userId, createdRole.getId());
            }

            // return success
            return buildCreatedResponse(TargetRole.builder().build());
        } catch (ServiceException e) {
            return buildErrorResponse();
        }
    }

    @GetMapping(value = "/roles", params = "userId", produces = "application/json")
    public ResponseEntity<AbstractServiceResponse> getRoleHistory(@RequestParam String userId) {
        log.info("REQUEST RECEIVED");
        if (userId == null || userId.isEmpty()) {
            log.debug("REQUEST: Failed validation");
            return buildBadReqResponse();
        }

        try {
            TargetRoleHistory serviceResponse = roleService.retrieveRoleHistory(Long.parseLong(userId));

            log.info(serviceResponse.toString());

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

    @GetMapping(value = "/role/newRequests", params = "rmId")
    public ResponseEntity<AbstractServiceResponse> retrieveNewRoleRequests(@RequestParam Long rmId) {
        try {

            if(requestValidator.invalid(rmId)){
                return buildBadReqResponse();
            }

            PotentialRoles roleRequests = roleService.retrieveNewRoleRequests(rmId);

            return buildOkResponse(roleRequests);

        } catch (ServiceException e){
            return buildErrorResponse();
        }
    }

    @GetMapping(value = "/roles", params = "roleType")
    public ResponseEntity<AbstractServiceResponse> getRolesByRoleType(@RequestParam RoleType roleType) {
        log.info("REQUEST RECIEVED");
        try {

            PotentialRoles potentialRoles = roleService.retrievePotentialRolesByRoleType(roleType);

            return buildOkResponse(potentialRoles);

        } catch (ServiceException e) {
            return buildErrorResponse();
        }
    }

    @GetMapping(value = "/role/id/{id}", produces = "application/json")
    public ResponseEntity<AbstractServiceResponse> getTargetRole(@PathVariable Long id) {
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

    @GetMapping(value = "/roles", produces = "application/json")
    public ResponseEntity<AbstractServiceResponse> getRoles() {
        try {
            PotentialRoles response = roleService.retrievePotentialRoles();

            return buildOkResponse(response);

        } catch (ServiceException e) {
            return buildErrorResponse();
        }
    }
}
