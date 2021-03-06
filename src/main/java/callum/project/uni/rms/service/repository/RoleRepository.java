package callum.project.uni.rms.service.repository;

import callum.project.uni.rms.common.RoleType;
import callum.project.uni.rms.service.repository.model.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    @Query(value = "SELECT r.* from role r join (SELECT ass.role_id FROM assignment ass WHERE ass.user_id = :id) ms ON r.id = ms.role_id", nativeQuery = true)
    List<Role> findRolesForUser(@Param("id") Long id);

    @Query(value = "SELECT r.* from role r where r.id not in (SELECT ass.role_id FROM assignment ass)", nativeQuery = true)
    List<Role> findPotentialRoles();

    List<Role> findAllByRoleType(RoleType roleType);
    
    @Query(value = "SELECT ro.* from role ro WHERE  ro.project_code = :projectCode AND ro.id IN (SELECT r.id FROM role r INNER JOIN assignment a on r.id = a.role_id)", nativeQuery = true)
    List<Role> findPotentialRolesByProjectCode(@Param("projectCode") String projectCode);

    List<Role> findAllByProjectCode(String projectCode);

    @Modifying(clearAutomatically = true)
    @Query(value = "update role r set r.is_role_open =:isRoleOpen where r.id =:roleId", nativeQuery = true)
    void updateRoleOpen(@Param("roleId") Long roleId, @Param("isRoleOpen") boolean isRoleOpen);

}
