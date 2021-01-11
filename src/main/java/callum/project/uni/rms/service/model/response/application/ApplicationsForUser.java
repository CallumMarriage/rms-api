package callum.project.uni.rms.service.model.response.application;

import callum.project.uni.rms.service.model.response.AbstractServiceResponse;
import callum.project.uni.rms.service.model.response.TargetApplication;
import callum.project.uni.rms.service.model.response.TargetUser;
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
