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
@Table(name = "ROLE")
public class Role {

    @Id
    private String id;

    private String roleName;

    private String description;

    private String projectCode;

    private String accountNumber;

    private Date startDate;

    private Date endDate;
}
