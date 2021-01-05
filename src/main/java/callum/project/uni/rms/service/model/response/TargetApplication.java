package callum.project.uni.rms.service.model.response;

import callum.project.uni.rms.service.repository.model.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@Builder
public class TargetApplication {
    
    private LocalDate applicationDate;

    private LocalDate lastUpdatedDate;

    private ApplicationStatus applicationStatus;
}
