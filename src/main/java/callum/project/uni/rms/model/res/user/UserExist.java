package callum.project.uni.rms.model.res.user;

import callum.project.uni.rms.model.res.AbstractServiceResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@Builder
public class UserExist extends AbstractServiceResponse {

    private boolean userExist;
}
