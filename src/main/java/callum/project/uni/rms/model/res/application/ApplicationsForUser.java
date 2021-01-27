package callum.project.uni.rms.model.res.application;

import callum.project.uni.rms.model.res.AbstractServiceResponse;
import callum.project.uni.rms.model.res.TargetApplication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@Builder
public class ApplicationsForUser extends AbstractServiceResponse {
    
    private List<TargetApplication> applicationInfoList;
}
