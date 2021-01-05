package callum.project.uni.rms.service.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TargetUser extends AbstractServiceResponse {

    private int id;

    private String currentRoleId;
}
