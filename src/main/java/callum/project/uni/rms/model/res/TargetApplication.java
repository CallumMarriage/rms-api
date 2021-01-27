package callum.project.uni.rms.model.res;

import callum.project.uni.rms.service.repository.model.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@Builder
public class TargetApplication extends AbstractServiceResponse{

    private Long applicantId;

    private LocalDate applicationDate;

    private LocalDate lastUpdatedDate;

    private Long applicationId;

    private ApplicationStatus applicationStatus;

    private Long roleId;

    private String accountId;

    private String projectId;
}
