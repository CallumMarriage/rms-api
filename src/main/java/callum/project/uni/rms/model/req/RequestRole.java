package callum.project.uni.rms.model.req;

import callum.project.uni.rms.common.RoleType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class RequestRole {
    
    @JsonProperty("roleName")
    private String roleName;

    @JsonProperty("projectName")
    private String projectName;

    @JsonProperty("roleType")
    private RoleType roleType;

    @JsonProperty("startDate")
    private LocalDate startDate;

    @JsonProperty("endDate")
    private LocalDate endDate;

    @JsonProperty("projectCode")
    private String projectCode;

    @JsonProperty("accountNumber")
    private String accountNumber;

    @JsonProperty("accountName")
    private String accountName;

    @JsonProperty("description")
    private String description;

    @JsonProperty("isRoleOpen")
    private boolean isRoleOpen;
}
