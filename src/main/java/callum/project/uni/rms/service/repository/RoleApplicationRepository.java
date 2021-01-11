package callum.project.uni.rms.service.repository;

import callum.project.uni.Application;
import callum.project.uni.rms.service.repository.model.RoleApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleApplicationRepository extends CrudRepository<RoleApplication, String> {

    int countDistinctByApplicantId(Long applicantId);
    
    List<RoleApplication> findAllByApplicantId(Long applicantId);
}
