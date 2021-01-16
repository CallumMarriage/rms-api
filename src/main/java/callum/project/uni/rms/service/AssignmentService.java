package callum.project.uni.rms.service;

import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.service.repository.AssignmentRepository;
import callum.project.uni.rms.service.repository.model.Assignment;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

import static callum.project.uni.rms.service.mapper.AssignmentMapper.buildAssignmentFromTarget;

@Service
@Slf4j
@AllArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    public void addNewAssignment(Long userId, Long roleId, LocalDate startDate, LocalDate endDate) throws ServiceException {
        try {
            Assignment assignment = buildAssignmentFromTarget(userId, roleId, startDate, endDate);

            assignmentRepository.save(assignment);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ServiceException("Issue adding new role");
        }
    }
}
