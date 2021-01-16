package callum.project.uni.rms.model.req;

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
public class ProjectCreateReq {

    @JsonProperty("startDate")
    private LocalDate startDate;

    @JsonProperty("endDate")
    private LocalDate endDate;

    @JsonProperty("accountNumber")
    private String accountNumber;

    @JsonProperty("projectCode")
    private String projectCode;

    @JsonProperty("projectName")
    private String projectName;

    @JsonProperty("description")
    private String description;

}
