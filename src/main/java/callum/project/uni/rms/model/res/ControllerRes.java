package callum.project.uni.rms.model.res;

import callum.project.uni.rms.service.model.response.AbstractServiceResponse;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ControllerRes {

    private final AbstractServiceResponse responseBody;
}
