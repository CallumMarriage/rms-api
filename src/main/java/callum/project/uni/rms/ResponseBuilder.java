package callum.project.uni.rms;

import callum.project.uni.rms.model.res.ControllerRes;
import callum.project.uni.rms.service.model.response.AbstractServiceResponse;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

    public static ResponseEntity<ControllerRes> buildErrorResponse() {
        return ResponseEntity
                .status(500)
                .build();
    }

    public static ResponseEntity<ControllerRes> buildBadReqResponse() {
        return ResponseEntity
                .status(400)
                .build();
    }

    public static ResponseEntity<ControllerRes> buildNotFoundResponse() {
        return ResponseEntity
                .status(404)
                .build();
    }

    public static ResponseEntity<ControllerRes> buildCreatedResponse(AbstractServiceResponse serviceResponse) {
        ControllerRes response = ControllerRes.builder()
                .responseBody(serviceResponse)
                .build();

        return ResponseEntity
                .status(201)
                .body(response);
    }

    public static ResponseEntity<ControllerRes> buildOkResponse(AbstractServiceResponse serviceResponse) {
        ControllerRes response = ControllerRes.builder()
                .responseBody(serviceResponse)
                .build();

        return ResponseEntity
                .status(200)
                .body(response);
    }
}
