package callum.project.uni.rms.service.model.response.role;

import callum.project.uni.rms.service.model.response.AbstractServiceResponse;
import callum.project.uni.rms.service.model.response.TargetRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@Builder
public class PotentialRoles extends AbstractServiceResponse {

    private List<TargetRole> potentialRoles;
}
