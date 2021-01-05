package callum.project.uni.rms.service.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role_application")
public class RoleApplication {

    @Id
    @Column(name = "application_id")
    private String applicationId;

    @Column(name = "applicant_id")
    private String applicantId;

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "project_id")
    private String projectId;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "application_date")
    private LocalDate applicationDate;

    @Column(name = "last_updated_date")
    private LocalDate lastUpdatedDate;

    @Column(name = "application_status")
    private ApplicationStatus applicationStatus;
}
