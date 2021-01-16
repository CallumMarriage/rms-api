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
public class AccountCreateReq {

    @JsonProperty("startDate")
    private LocalDate startDate;

    @JsonProperty("endDate")
    private LocalDate endDate;

    @JsonProperty("accountNumber")
    private String accountNumber;

    @JsonProperty("accountName")
    private String accountName;

    @JsonProperty("description")
    private String description;
}
