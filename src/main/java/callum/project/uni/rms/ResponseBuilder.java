package callum.project.uni.rms;

import callum.project.uni.rms.model.ControllerResponse;
import callum.project.uni.rms.service.model.response.AbstractServiceResponse;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

    public static ResponseEntity<ControllerResponse> buildErrorResponse(){
        return ResponseEntity
                .status(500)
                .build();
    }

    public static ResponseEntity<ControllerResponse> buildBadReqResponse(){
        return ResponseEntity
                .status(400)
                .build();
    }

    public static ResponseEntity<ControllerResponse> buildNotFoundResponse(){
        return ResponseEntity
                .status(404)
                .build();
    }

    public static ResponseEntity<ControllerResponse> buildOkResponse(AbstractServiceResponse serviceResponse){
        ControllerResponse response = ControllerResponse.builder()
                .responseBody(serviceResponse)
                .build();

        return ResponseEntity
                .status(200)
                .body(response);
    }
}
