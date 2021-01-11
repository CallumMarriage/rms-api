package callum.project.uni.rms.service.repository;

import callum.project.uni.rms.service.repository.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    
    Optional<User> findByGoogleId(String googleId);
}
