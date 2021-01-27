package callum.project.uni.rms.service.repository;

import callum.project.uni.rms.service.repository.model.RoleSubmission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleSubmissionRepository extends CrudRepository<RoleSubmission, Long> {

    @Query(value = "SELECT rs.* FROM role_submission rs INNER JOIN user u ON rs.candidate_id = u.id WHERE u.resource_manager_id = :rm_id)", nativeQuery = true)
    public List<RoleSubmission> findAllSubmissionsForRm(@Param("rm_id") Long rmid);
}
