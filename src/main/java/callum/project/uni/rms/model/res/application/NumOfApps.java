package callum.project.uni.rms.model.res.application;

import callum.project.uni.rms.model.res.AbstractServiceResponse;
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
