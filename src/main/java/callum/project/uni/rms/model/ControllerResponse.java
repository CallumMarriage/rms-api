package callum.project.uni.rms.model;

import callum.project.uni.rms.service.model.response.AbstractServiceResponse;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ControllerResponse {

    private final AbstractServiceResponse responseBody;
}
