package callum.project.uni.rms.service.repository;

import callum.project.uni.rms.service.repository.model.RoleApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleApplicationRepository extends CrudRepository<RoleApplication, String> {
}
