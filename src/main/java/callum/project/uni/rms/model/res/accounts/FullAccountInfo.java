package callum.project.uni.rms.model.res.accounts;

import callum.project.uni.rms.model.res.AbstractServiceResponse;
import callum.project.uni.rms.model.res.TargetAccount;
import callum.project.uni.rms.model.res.TargetProject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@Builder
public class FullAccountInfo extends AbstractServiceResponse {

    private TargetAccount account;

    private List<TargetProject> projectList;
}
