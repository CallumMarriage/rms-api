package callum.project.uni.rms.model.res.application;

import callum.project.uni.rms.model.res.AbstractServiceResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class HasUserAlreadyApplied extends AbstractServiceResponse {

    private boolean userAlreadyApplied;
}
