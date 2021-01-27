package callum.project.uni.rms.service.repository;

import callum.project.uni.rms.service.repository.model.RoleApplication;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoleApplicationRepository extends CrudRepository<RoleApplication, Long> {

    int countDistinctByApplicantId(Long applicantId);
    
    List<RoleApplication> findAllByApplicantId(Long applicantId);

    @Modifying(clearAutomatically = true)
    @Query(value = "update role_application ra set ra.application_status = 3, ra.last_updated_date = :currentDate  where ra.application_id = :appId", nativeQuery = true)
    void updateApplicationToBeConfirmed(@Param("appId") Long appId,
                                        @Param("currentDate") Date currentDate);


    Optional<RoleApplication> findFirstByRoleIdAndApplicantId(Long roleId, Long applicantId);

    @Modifying(clearAutomatically = true)
    @Query(value = "update role_application ra set ra.application_status = 2, ra.last_updated_date = :currentDate where ra.application_id = :appId", nativeQuery = true)
    void updateApplicationToBeRejected(@Param("appId") Long appId,
                                       @Param("currentDate") Date currentDate);

    @Modifying(clearAutomatically = true)
    @Query(value = "update role_application ra set ra.application_status = 1, ra.last_updated_date = :currentDate where ra.application_id = :appId", nativeQuery = true)
    void updateApplicationToInReview(@Param("appId") Long appId,
                                     @Param("currentDate") Date currentDate);

    @Modifying(clearAutomatically = true)
    @Query(value = "SELECT ra.* FROM role_application ra INNER JOIN user u ON ra.applicant_id = u.id WHERE u.resource_manager_id = :rm_id", nativeQuery = true)
    List<RoleApplication> findAllByResourceManager(@Param("rm_id") Long rmId);
}
