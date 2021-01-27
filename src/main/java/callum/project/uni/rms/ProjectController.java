package callum.project.uni.rms;

import callum.project.uni.rms.model.req.ProjectCreateReq;
import callum.project.uni.rms.model.res.AbstractServiceResponse;
import callum.project.uni.rms.service.ProjectAndAccountService;
import callum.project.uni.rms.service.ProjectService;
import callum.project.uni.rms.service.RoleService;
import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.model.res.TargetProject;
import callum.project.uni.rms.model.res.TargetRole;
import callum.project.uni.rms.model.res.accounts.AccountsWithProjects;
import callum.project.uni.rms.model.res.projects.FullProjectInfo;
import callum.project.uni.rms.validator.RequestValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static callum.project.uni.rms.ResponseBuilder.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final RoleService roleService;
    private final RequestValidator requestValidator;
    private final ProjectAndAccountService projectAndAccountService;

    @GetMapping(value = "/accounts/projects", produces = "application/json")
    public ResponseEntity<AbstractServiceResponse> retrieveAccountsAndProjects(@RequestParam Long projectManagerId) {

        try {
            if (requestValidator.invalid(projectManagerId)) {
                return buildBadReqResponse();
            }

            //Call Service to get response object
            AccountsWithProjects serviceResponse = projectAndAccountService.retrieveAccountsWithProjects(projectManagerId);

            //Return ok response
            return buildOkResponse(serviceResponse);
        } catch (ServiceException e) {
            //Return 500 response
            return buildErrorResponse();
        }
    }

    @GetMapping(value = "/projects/id/{projectCode}", produces = "application/json")
    public ResponseEntity<AbstractServiceResponse> retrieveTargetProject(@PathVariable String projectCode) {


        try {
            //Call Service to get response object
            TargetProject serviceResponse = projectService.getProjectById(projectCode);
            List<TargetRole> rolesForProject = roleService.retrieveAllRolesForProjectCode(projectCode);

            FullProjectInfo fullProjectInfo = FullProjectInfo.builder()
                    .roleList(rolesForProject)
                    .targetProject(serviceResponse)
                    .build();

            //Return ok response
            return buildOkResponse(fullProjectInfo);
        } catch (ServiceException e) {
            //Return 500 response
            return buildErrorResponse();
        }
    }

    @PostMapping(value = "/project", produces = "application/json")
    public ResponseEntity<AbstractServiceResponse> addNewProject(@RequestBody ProjectCreateReq projectCreateReq) {
        try {

            if (requestValidator.validateProjectCreateReq(projectCreateReq)) {
                return buildBadReqResponse();
            }

            TargetProject newProject = projectService.addNewProject(projectCreateReq);
            return buildCreatedResponse(newProject);
        } catch (ServiceException e) {
            //Return 500 response
            return buildErrorResponse();
        }
    }
}
