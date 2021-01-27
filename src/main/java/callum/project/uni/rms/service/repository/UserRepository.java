package callum.project.uni.rms.service.repository;

import callum.project.uni.rms.service.repository.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    
    Optional<User> findBySsoId(String ssoId);

    @Modifying(clearAutomatically = true)
    @Query("update User u set u.currentRoleId =:newRoleId where u.id =:userId")
    void updateUserCurrentRoleId(@Param("userId") Long userId, @Param("newRoleId") Long newRoleId);

    List<User> findAllByResourceManagerId(Long rmId);

    @Query(value = "SELECT u.* FROM User u where u.full_name LIKE %?1", nativeQuery = true)
    List<User> findAllByFullName(String fullName);
}
