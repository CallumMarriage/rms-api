package callum.project.uni.rms.service.repository;

import callum.project.uni.rms.service.repository.model.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<Project, String> {

    List<Project> findAllByAccountNumber(String accountNumber);
}
