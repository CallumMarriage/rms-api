package callum.project.uni.rms.service.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class TargetProject {

    private String projectName;

    private String projectCode;

    private String accountNumber;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

}
