package callum.project.uni.rms.service.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@Builder
public class TargetProject extends AbstractServiceResponse {

    private String projectName;

    private String projectCode;

    private String accountNumber;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

}
