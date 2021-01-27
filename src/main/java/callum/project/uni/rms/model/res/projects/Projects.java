package callum.project.uni.rms.model.res.projects;

import callum.project.uni.rms.model.res.AbstractServiceResponse;
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
public class Projects extends AbstractServiceResponse {

    private List<TargetProject> projects;
}
