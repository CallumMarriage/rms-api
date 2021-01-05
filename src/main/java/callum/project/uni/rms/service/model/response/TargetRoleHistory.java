package callum.project.uni.rms.service.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;


@AllArgsConstructor
@Builder
@Data
@ToString
public class TargetRoleHistory extends AbstractServiceResponse {

    private List<TargetRole> rolehistory;
}
