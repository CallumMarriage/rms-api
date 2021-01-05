package callum.project.uni.rms.service;

import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.service.model.response.TargetProject;
import callum.project.uni.rms.service.repository.ProjectRepository;
import callum.project.uni.rms.service.repository.model.Project;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static callum.project.uni.rms.service.mapper.ProjectMapper.mapProjectToTargetProject;

@Service
@Slf4j
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public TargetProject getProjectById(String id) throws ServiceException {
        try {
            Optional<Project> accountOptional = projectRepository.findById(id);
            if (accountOptional.isEmpty()) {
                return null;
            }

            return mapProjectToTargetProject(accountOptional.get());
        } catch (RuntimeException e) {
            throw new ServiceException("Issue retrieving account");
        } catch (Exception e) {
            throw new ServiceException("Issue mapping account");
        }
    }
}
