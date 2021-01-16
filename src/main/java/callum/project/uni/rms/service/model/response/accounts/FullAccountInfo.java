package callum.project.uni.rms.service.model.response.accounts;

import callum.project.uni.rms.service.model.response.AbstractServiceResponse;
import callum.project.uni.rms.service.model.response.TargetAccount;
import callum.project.uni.rms.service.model.response.TargetProject;
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
