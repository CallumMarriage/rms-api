package callum.project.uni.rms;

import callum.project.uni.rms.model.res.ControllerRes;
import callum.project.uni.rms.service.AccountService;
import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.service.model.response.TargetAccount;
import callum.project.uni.rms.service.model.response.accounts.AccountList;
import callum.project.uni.rms.validator.RequestValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static callum.project.uni.rms.ResponseBuilder.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final RequestValidator requestValidator;

    @GetMapping(value = "/account/id/{id}", produces = "application/json")
    public ResponseEntity<ControllerRes> retrieveTargetAccount(@PathVariable String id) {
        
        //Validate Request id
        if (requestValidator.validateGetByIdReq(id)) {
            //Return 400 response
            return buildBadReqResponse();
        }

        try {
            //Call Service to get response object
            TargetAccount serviceResponse = accountService.getTargetAccountById(id);
            //Return ok response
            return buildOkResponse(serviceResponse);
        } catch (ServiceException e) {
            //Return 500 response
            return buildErrorResponse();
        }
    }

    @GetMapping(value = "/accounts", produces = "application/json")
    public ResponseEntity<ControllerRes> retrieveAccountList(){
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

    @GetMapping(value = "/accounts", params = "accountName",  produces = "application/json" )
    public ResponseEntity<ControllerRes> retrieveAccountList(@RequestParam String accountName){
        try {
            //Call Service to get response object
            AccountList serviceResponse = accountService.getAccountList(accountName);
            //Return ok response
            return buildOkResponse(serviceResponse);
        } catch (ServiceException e) {
            //Return 500 response
            return buildErrorResponse();
        }
    }
}
