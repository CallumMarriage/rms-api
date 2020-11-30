package callum.project.uni.rms.service.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class TargetRole {

    private String id;

    private String roleName;

    private LocalDate startDate;

    private LocalDate endDate;

    private String projectCode;

    private String accountNumber;

    private String description;

}
