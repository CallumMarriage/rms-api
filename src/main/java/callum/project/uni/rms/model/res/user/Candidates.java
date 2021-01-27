package callum.project.uni.rms.model.res.user;

import callum.project.uni.rms.model.res.AbstractServiceResponse;
import callum.project.uni.rms.model.res.TargetUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Candidates extends AbstractServiceResponse {
    private List<TargetUser> candidates;
}
