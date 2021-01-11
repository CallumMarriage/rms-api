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
public class AppCreateReq {

    @JsonProperty("applicantId")
    private String applicantId;

    @JsonProperty("roleId")
    private String roleId;

    @JsonProperty("projectCode")
    private String projectCode;

    @JsonProperty("accountNumber")
    private String accountNumber;
}
