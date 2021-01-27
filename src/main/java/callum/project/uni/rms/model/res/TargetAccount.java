package callum.project.uni.rms.model.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@Builder
public class TargetAccount extends AbstractServiceResponse {

    private Long accountManagerId;
    
    private String accountName;

    private String accountCode;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

}
