package callum.project.uni.rms;

import callum.project.uni.rms.model.res.AbstractServiceResponse;
import callum.project.uni.rms.model.res.TargetUser;
import callum.project.uni.rms.service.UserService;
import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.model.res.user.Candidates;
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
public class CandidateController {

    private final RequestValidator validator;
    private final UserService userService;

    @GetMapping(value = "/candidates")
    public ResponseEntity<AbstractServiceResponse> retrieveCandidatesByResourceManager(@RequestParam Long rmId){
        if(validator.invalid(rmId)){
            return buildBadReqResponse();
        }

        try {
            Candidates serviceResponse = userService.retrieveUserByRmId(rmId);

            return buildOkResponse(serviceResponse);
        } catch (ServiceException e) {
            return buildErrorResponse();
        }
    }

    @PostMapping(value = "/candidates")
    public ResponseEntity<AbstractServiceResponse> retrieveCandidatesByFullName(@RequestBody String fullName){

        if(validator.invalid(fullName)){
            return buildBadReqResponse();
        }

        try {
            Candidates serviceResponse = userService.retrieveUserByFullName(fullName);

            if(serviceResponse == null){
                return buildNotFoundResponse();
            }

            return buildOkResponse(serviceResponse);
        } catch (ServiceException e) {
            return buildErrorResponse();
        }
    }
}
