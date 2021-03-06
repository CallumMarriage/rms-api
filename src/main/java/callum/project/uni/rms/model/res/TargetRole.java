package callum.project.uni.rms.model.res;

import callum.project.uni.rms.common.CapgeminiOffice;
import callum.project.uni.rms.common.RoleType;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Data
@ToString
public class TargetRole extends AbstractServiceResponse {

    private Long id;

    private String roleName;

    private String accountName;

    private String projectName;

    private RoleType roleType;

    private LocalDate startDate;

    private LocalDate endDate;

    private String projectCode;

    private String accountNumber;

    private String description;

    private CapgeminiOffice closestCapOffice;

    private Boolean isRoleOpen;
}
