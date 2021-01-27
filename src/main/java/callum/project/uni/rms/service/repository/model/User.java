package callum.project.uni.rms.service.repository.model;

import callum.project.uni.rms.common.CapgeminiOffice;
import callum.project.uni.rms.common.RoleType;
import callum.project.uni.rms.common.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "resource_manager_id")
    private Long resourceManagerId;

    @Column(name = "sso_id")
    private String ssoId;

    @Column(name = "current_role_id")
    private Long currentRoleId;
    
    @Column(name = "user_type")
    private UserType userType;

    @Column(name = "user_specialism")
    private RoleType userSpecialism;
    
    @Column(name = "closest_cap_office")
    private CapgeminiOffice closestCapOffice;
}
