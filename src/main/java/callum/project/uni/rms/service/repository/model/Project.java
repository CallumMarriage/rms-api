package callum.project.uni.rms.service.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "PROJECT")
public class Project {

    private String projectName;

    @Id
    private String projectCode;

    private String description;

    private Date startDate;

    private Date endDate;

}
