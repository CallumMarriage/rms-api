package callum.project.uni.rms;

import callum.project.uni.rms.model.req.ProjectCreateReq;
import callum.project.uni.rms.model.res.ControllerRes;
import callum.project.uni.rms.service.ProjectService;
import callum.project.uni.rms.service.RoleService;
import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.service.model.response.TargetAccount;
import callum.project.uni.rms.service.model.response.TargetProject;
import callum.project.uni.rms.service.model.response.TargetRole;
import callum.project.uni.rms.service.model.response.projects.FullProjectInfo;
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

    @GetMapping(value = "/projects/id/{projectCode}", produces = "application/json")
    public ResponseEntity<ControllerRes> retrieveTargetAccount(@PathVariable String projectCode) {


        try {
            //Call Service to get response object
            TargetProject serviceResponse = projectService.getProjectById(projectCode);
            List<TargetRole> rolesForProject = roleService.retrieveForProjectId(projectCode);

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
    public ResponseEntity<ControllerRes> addNewAccount(@RequestBody ProjectCreateReq projectCreateReq) {
        try {

            if (requestValidator.invalid(projectCreateReq.getAccountNumber()) ||
                    requestValidator.invalid(projectCreateReq.getProjectCode()) ||
                    requestValidator.invalid(projectCreateReq.getProjectName()) ||
                    requestValidator.invalid(projectCreateReq.getStartDate())) {
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
