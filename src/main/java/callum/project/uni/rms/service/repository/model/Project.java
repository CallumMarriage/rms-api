package callum.project.uni.rms.service.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "project")
public class Project {

    @Column(name = "project_manager_id")
    private Long projectManagerId;

    @Column(name = "project_name")
    private String projectName;

    @Id
    @Column(name = "project_code")
    private String projectCode;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "description", columnDefinition="TEXT")
    private String description;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

}
