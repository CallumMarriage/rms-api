package callum.project.uni.rms.model.res.projects;

import callum.project.uni.rms.model.res.AbstractServiceResponse;
import callum.project.uni.rms.model.res.TargetProject;
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
public class FullProjectInfo extends AbstractServiceResponse {

    private TargetProject targetProject;

    private List<TargetRole> roleList;
}
