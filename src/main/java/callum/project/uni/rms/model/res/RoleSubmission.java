package callum.project.uni.rms.model.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class RoleSubmission {

    private TargetRole role;

    private Long userId;
}
