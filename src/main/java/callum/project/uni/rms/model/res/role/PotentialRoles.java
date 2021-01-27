package callum.project.uni.rms.model.res.role;

import callum.project.uni.rms.model.res.AbstractServiceResponse;
import callum.project.uni.rms.model.res.RoleSubmission;
import callum.project.uni.rms.model.res.TargetRole;
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
