package callum.project.uni.rms;

import callum.project.uni.rms.model.ControllerResponse;
import callum.project.uni.rms.service.AccountService;
import callum.project.uni.rms.service.ProjectService;
import callum.project.uni.rms.service.RoleService;
import callum.project.uni.rms.service.UserService;
import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.service.model.request.UserCreateRequest;
import callum.project.uni.rms.service.model.response.*;
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
public class BasicUserInfoController {

    private final RequestValidator requestValidator;

    private final UserService userService;

    private final RoleService roleService;

    private final AccountService accountService;

    private final ProjectService projectService;

    @PostMapping(value = "/user")
    public ResponseEntity<ControllerResponse> addNewUser(@RequestBody UserCreateRequest userCreateRequest) {
        if (requestValidator.validateUserCreateRequest(userCreateRequest)) {
            return buildBadReqResponse();
        }

        String googleId = userCreateRequest.getGoogleId();

        try {
            if (userService.userExists(googleId)) {
                TargetUser user = userService.retrieveUserDetails(googleId);
                return ResponseEntity.status(202)
                        .body(createControllerResponse(user));
            }

            TargetUser user = userService.createUser(googleId);

            return buildOkResponse(buildUserInfoResponse(user));
        } catch (ServiceException e) {
            return buildErrorResponse();
        }
    }

    @GetMapping(value = "/user/info")
    public ResponseEntity<ControllerResponse> getUserInfo(@RequestParam String userId){

        log.info("Request received");
        if (requestValidator.validateGetByIdReq(userId)){
            return buildBadReqResponse();
        }

        try {
            TargetUser user = userService.retrieveUserDetails(userId);
            return ResponseEntity.status(200)
                    .body(createControllerResponse(user));
        }  catch (ServiceException e) {
            return buildErrorResponse();
        }
    }

    /**
     * Create Controller response model using user data. This model includes a full role history.
     *
     * @param user user data.
     * @return controller response model
     */
    private ControllerResponse createControllerResponse(TargetUser user) throws ServiceException {
        return ControllerResponse.builder()
                .responseBody(buildUserInfoResponse(user))
                .build();
    }

    private FullUserInfo buildUserInfoResponse( TargetUser user) throws ServiceException {
        String currentRoleId = user.getCurrentRoleId();

        TargetRole currentRole = null;

        log.info("Making request to role service for role history");
        Optional<TargetRole> role = roleService.retrieveTargetRole(currentRoleId);

        if(role.isPresent()){
            currentRole = role.get();
        }

        TargetAccount currentAccount = null;
        TargetProject currentProject = null;

        if (currentRole != null) {
            log.info("Making request to account service");
            currentAccount = accountService.getTargetAccountById(currentRole.getAccountNumber());

            log.info("Making request to project api");
            currentProject = projectService.getProjectById(currentRole.getProjectCode());
        }

        log.info("Build full user info object");
        return FullUserInfo.builder()
                .user(user)
                .currentRole(currentRole)
                .currentAccount(currentAccount)
                .currentProject(currentProject)
                .build();
    }


}
