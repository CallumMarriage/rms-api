package callum.project.uni.rms.service.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class TargetAccount {

    private String accountName;

    private String accountCode;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

}
