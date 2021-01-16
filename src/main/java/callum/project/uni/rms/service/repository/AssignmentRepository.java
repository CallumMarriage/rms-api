package callum.project.uni.rms.service.repository;

import callum.project.uni.rms.service.repository.model.Assignment;
import callum.project.uni.rms.service.repository.model.AssignmentId;
import org.springframework.data.repository.CrudRepository;

public interface AssignmentRepository extends CrudRepository<Assignment, AssignmentId> {
}
