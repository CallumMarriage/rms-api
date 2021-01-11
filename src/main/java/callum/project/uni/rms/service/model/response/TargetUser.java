package callum.project.uni.rms.service.model.response;

import callum.project.uni.rms.service.repository.model.CapgeminiOffice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TargetUser extends AbstractServiceResponse {

    private Long id;

    private String currentRoleId;

    private Long resourceManagerId;

    private CapgeminiOffice closestCapOffice;
}
