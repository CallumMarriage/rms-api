package callum.project.uni.rms.service.model.response;

import callum.project.uni.rms.service.repository.model.CapgeminiOffice;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Data
@ToString
public class TargetRole extends AbstractServiceResponse {

    private String id;

    private String roleName;

    private String accountName;

    private String projectName;

    private LocalDate startDate;

    private LocalDate endDate;

    private String projectCode;

    private String accountNumber;

    private String description;

    private CapgeminiOffice closestCapOffice;

}
