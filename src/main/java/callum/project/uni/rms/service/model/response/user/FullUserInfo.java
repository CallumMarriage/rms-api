package callum.project.uni.rms.service.model.response.user;

import callum.project.uni.rms.service.model.response.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@Builder
public class FullUserInfo extends AbstractServiceResponse {
    
    private final TargetUser user;

    private final TargetRole currentRole;

    private final TargetAccount currentAccount;

    private final TargetProject currentProject;
}
