package callum.project.uni.rms;

import callum.project.uni.rms.model.res.AbstractServiceResponse;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

    public static ResponseEntity<AbstractServiceResponse> buildErrorResponse() {
        return ResponseEntity
                .status(500)
                .build();
    }

    public static ResponseEntity<AbstractServiceResponse> buildBadReqResponse() {
        return ResponseEntity
                .status(400)
                .build();
    }

    public static ResponseEntity<AbstractServiceResponse> buildNotFoundResponse() {
        return ResponseEntity
                .status(404)
                .build();
    }

    public static ResponseEntity<AbstractServiceResponse> buildCreatedResponse(AbstractServiceResponse serviceResponse) {
        return ResponseEntity
                .status(201)
                .body(serviceResponse);
    }

    public static ResponseEntity<AbstractServiceResponse> buildOkResponse(AbstractServiceResponse serviceResponse) {

        return ResponseEntity
                .status(200)
                .body(serviceResponse);
    }
}
