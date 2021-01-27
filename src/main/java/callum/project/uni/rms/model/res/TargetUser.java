package callum.project.uni.rms.model.res;

import callum.project.uni.rms.common.CapgeminiOffice;
import callum.project.uni.rms.common.RoleType;
import callum.project.uni.rms.common.UserType;
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

    private UserType userType;

    private String fullName;

    private RoleType userSpecialism;
}
