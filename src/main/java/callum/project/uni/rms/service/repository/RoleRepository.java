package callum.project.uni.rms.service.repository;

import callum.project.uni.rms.service.repository.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {

}
