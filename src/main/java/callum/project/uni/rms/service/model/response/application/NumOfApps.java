package callum.project.uni.rms.service.model.response.application;

import callum.project.uni.rms.service.model.response.AbstractServiceResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@Builder
public class NumOfApps extends AbstractServiceResponse {

    private Integer numOfApplications;
}
