package callum.project.uni.rms.service.model.response;

import callum.project.uni.rms.common.CapgeminiOffice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TargetUser extends AbstractServiceResponse {

    private Long id;

    private Long currentRoleId;

    private Long resourceManagerId;

    private CapgeminiOffice closestCapOffice;
}
