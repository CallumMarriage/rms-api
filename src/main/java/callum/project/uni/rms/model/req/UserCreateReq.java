package callum.project.uni.rms.model.req;

import callum.project.uni.rms.common.RoleType;
import callum.project.uni.rms.common.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class UserCreateReq {

    @JsonProperty("ssoId")
    private String ssoId;

    @JsonProperty("currentRoleId")
    private Long currentRoleId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("userType")
    private UserType userType;

    @JsonProperty("userSpecialism")
    private RoleType userSpecialism;
}
