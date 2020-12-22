package callum.project.uni.rms;

import callum.project.uni.rms.service.AccountService;
import callum.project.uni.rms.service.ProjectService;
import callum.project.uni.rms.service.RMSService;
import callum.project.uni.rms.service.model.TargetAccount;
import callum.project.uni.rms.service.model.TargetProject;
import callum.project.uni.rms.service.model.TargetRole;
import callum.project.uni.rms.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
@AllArgsConstructor
public class Controller {

    private final RMSService roleService;
//    private final AccountService accountService;
//    private final ProjectService projectService;


    @GetMapping(value = "/account/id/{id}", produces = "application/json")
    public @ResponseBody TargetAccount getTargetAccount(@PathVariable String id){
        return roleService.retrieveTargetAccount(id);
    }

    @CrossOrigin(value = "http://localhost:3000")
    @GetMapping(value = "/role/id/{id}", produces = "application/json")
    public @ResponseBody TargetRole getTargetRole(@PathVariable String id){
        log.info("REQUEST RECEIVED");
        return roleService.retrieveTargetRole(id);
    }
//
//    @CrossOrigin
//    @GetMapping(value = "/account/id/{accountId}", produces = "application/json")
//    public @ResponseBody
//    TargetAccount getTargetAccount(@PathVariable String accountId){
//        return accountService.getTargetAccountById(accountId);
//    }
//
//    @CrossOrigin
//    @GetMapping(value = "/project/id/{projectId}", produces = "application/json")
//    public @ResponseBody
//    TargetProject getTargetProject(@PathVariable String projectId){
//        return projectService.getTargetProjectById(projectId);
//    }
//
//
}
