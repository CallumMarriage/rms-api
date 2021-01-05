package callum.project.uni.rms.service.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@Builder
public class TargetAccount extends AbstractServiceResponse {

    private String accountName;

    private String accountCode;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

}
