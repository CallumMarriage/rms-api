package callum.project.uni.rms;

import callum.project.uni.rms.model.res.*;
import callum.project.uni.rms.service.AccountService;
import callum.project.uni.rms.service.ProjectService;
import callum.project.uni.rms.service.RoleService;
import callum.project.uni.rms.service.UserService;
import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.model.req.UserCreateReq;
import callum.project.uni.rms.model.res.user.FullUserInfo;
import callum.project.uni.rms.model.res.user.UserExist;
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

    @GetMapping(value = "/user/exists")
    public ResponseEntity<AbstractServiceResponse> doesUserExists(@RequestParam("ssoId") String ssoId){
        log.info(ssoId);
        if(requestValidator.invalid(ssoId)){
            return buildBadReqResponse();
        }

        try {
            UserExist userExist = userService.userExists(ssoId);
            return buildOkResponse(userExist);
        } catch (ServiceException e){
            return buildErrorResponse();
        }
    }

    @PostMapping(value = "/user")
    public ResponseEntity<AbstractServiceResponse> addNewUser(@RequestBody UserCreateReq userCreateRequest) {
        if (requestValidator.validateUserCreateRequest(userCreateRequest)) {
            return buildBadReqResponse();
        }

        try {
            TargetUser user = userService.createUser(userCreateRequest);
            return buildCreatedResponse(buildUserInfoResponse(user));
        } catch (ServiceException e) {
            return buildErrorResponse();
        }
    }

    @GetMapping(value = "/user/info")
    public ResponseEntity<AbstractServiceResponse> getUserInfo(@RequestParam String ssoId){

        if (requestValidator.validateGetByIdReq(ssoId)){
            return buildBadReqResponse();
        }

        try {
            TargetUser user = userService.retrieveUserDetailsBySsoId(ssoId);
            return ResponseEntity.status(200)
                    .body(buildUserInfoResponse(user));
        }  catch (ServiceException e) {
            log.error(e.getMessage());
            return buildErrorResponse();
        }
    }

    @GetMapping(value = "/user/resourceManager/:id")
    public ResponseEntity<AbstractServiceResponse> getResourceManagerName(@PathVariable Long rmId){
        if (rmId == null){
            return buildBadReqResponse();
        }

        try {
            TargetUser user = userService.retrieveUserByInternalId(rmId);
            return buildOkResponse(user);
        }  catch (ServiceException e) {
            log.error(e.getMessage());
            return buildErrorResponse();
        }
    }

    private FullUserInfo buildUserInfoResponse(TargetUser user) throws ServiceException {
        Long currentRoleId = user.getCurrentRoleId();

        TargetRole currentRole = null;
        TargetAccount currentAccount = null;
        TargetProject currentProject = null;

        if (currentRoleId != null) {
            log.debug("Making request to role service for role history");
            Optional<TargetRole> role = roleService.retrieveTargetRole(currentRoleId);

            if(role.isEmpty()){
                throw new ServiceException("Error finding current role");
            }

            currentRole = role.get();

            log.debug("Making request to account service");
            currentAccount = accountService.getTargetAccountById(currentRole.getAccountNumber());

            log.debug("Making request to project api");
            currentProject = projectService.getProjectById(currentRole.getProjectCode());
        }

        log.debug("Build full user info object");
        return FullUserInfo.builder()
                .user(user)
                .currentRole(currentRole)
                .currentAccount(currentAccount)
                .currentProject(currentProject)
                .build();
    }


}
