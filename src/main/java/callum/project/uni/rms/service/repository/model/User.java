package callum.project.uni.rms.service.repository.model;

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

    @Column(name = "resource_manager_id")
    private Long resourceManagerId;

    @Column(name = "google_id")
    private String googleId;

    @Column(name = "current_role_id")
    private String currentRoleId;

    @Column(name = "closest_cap_office")
    private CapgeminiOffice closestCapOffice;
}
