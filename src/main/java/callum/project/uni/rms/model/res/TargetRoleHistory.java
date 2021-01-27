package callum.project.uni.rms.model.res;

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
