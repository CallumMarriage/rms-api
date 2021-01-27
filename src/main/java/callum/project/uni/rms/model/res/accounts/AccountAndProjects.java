package callum.project.uni.rms.model.res.accounts;

import callum.project.uni.rms.model.res.AbstractServiceResponse;
import callum.project.uni.rms.model.res.TargetAccount;
import callum.project.uni.rms.model.res.TargetProject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
public class AccountAndProjects extends AbstractServiceResponse {

    private TargetAccount targetAccount;

    private List<TargetProject> projects;

    public void addProjectToProjects(TargetProject newProject){
        projects.add(newProject);
    }
}
