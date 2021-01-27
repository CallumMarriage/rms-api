package callum.project.uni.rms.model.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class RoleAppUpdateStatusReq {

    @JsonProperty("applicationId")
    private Long applicationId;

    @JsonProperty("authorizerId")
    private Long authorizerId;

}
