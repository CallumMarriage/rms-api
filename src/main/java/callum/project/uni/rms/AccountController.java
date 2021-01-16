package callum.project.uni.rms;

import callum.project.uni.rms.model.req.AccountCreateReq;
import callum.project.uni.rms.model.res.ControllerRes;
import callum.project.uni.rms.service.AccountService;
import callum.project.uni.rms.service.ProjectService;
import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.service.model.response.TargetAccount;
import callum.project.uni.rms.service.model.response.TargetProject;
import callum.project.uni.rms.service.model.response.accounts.AccountList;
import callum.project.uni.rms.service.model.response.accounts.FullAccountInfo;
import callum.project.uni.rms.service.repository.model.Project;
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
public class AccountController {

    private final AccountService accountService;
    private final ProjectService projectService;
    private final RequestValidator requestValidator;

    @GetMapping(value = "/account/id/{accountNumber}", produces = "application/json")
    public ResponseEntity<ControllerRes> retrieveTargetAccount(@PathVariable String accountNumber) {

        //Validate Request id
        if (requestValidator.validateGetByIdReq(accountNumber)) {
            //Return 400 response
            return buildBadReqResponse();
        }

        try {
            //Call Service to get response object
            TargetAccount serviceResponse = accountService.getTargetAccountById(accountNumber);
            if (serviceResponse == null) {
                return buildNotFoundResponse();
            }
            List<TargetProject> projectsForAccount = projectService.getProjectListForAccount(serviceResponse.getAccountCode());
            FullAccountInfo fullAccountInfo = FullAccountInfo.builder()
                    .account(serviceResponse)
                    .projectList(projectsForAccount)
                    .build();
            //Return ok response
            return buildOkResponse(fullAccountInfo);
        } catch (ServiceException e) {
            //Return 500 response
            return buildErrorResponse();
        }
    }

    @GetMapping(value = "/accounts", produces = "application/json")
    public ResponseEntity<ControllerRes> retrieveAccountList() {
        try {
            //Call Service to get response object
            AccountList serviceResponse = accountService.getAccountList();

            //Return ok response
            return buildOkResponse(serviceResponse);
        } catch (ServiceException e) {
            //Return 500 response
            return buildErrorResponse();
        }
    }

    @GetMapping(value = "/account", params = "accountName", produces = "application/json")
    public ResponseEntity<ControllerRes> retrieveAccountList(@RequestParam String accountName) {
        try {
            //Call Service to get response object
            TargetAccount serviceResponse = accountService.getAccountByName(accountName);

            if (serviceResponse == null) {
                return buildNotFoundResponse();
            }
            //Return ok response
            return buildOkResponse(serviceResponse);
        } catch (ServiceException e) {
            //Return 500 response
            return buildErrorResponse();
        }
    }

    @PostMapping(value = "/account", produces = "application/json")
    public ResponseEntity<ControllerRes> addNewAccount(@RequestBody AccountCreateReq accountCreateReq) {
        try {

            if (requestValidator.invalid(accountCreateReq.getAccountNumber()) ||
                    requestValidator.invalid(accountCreateReq.getAccountName()) ||
                    requestValidator.invalid(accountCreateReq.getStartDate())) {
                return buildBadReqResponse();
            }

            TargetAccount newAccount = accountService.addNewAccount(accountCreateReq);
            return buildCreatedResponse(newAccount);
        } catch (ServiceException e) {
            //Return 500 response
            return buildErrorResponse();
        }
    }
}
