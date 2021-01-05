package callum.project.uni.rms.service.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class UserCreateRequest {

    private String googleId;
}
