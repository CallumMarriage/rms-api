package callum.project.uni.rms.service;

import callum.project.uni.rms.service.mapper.ProjectMapper;
import callum.project.uni.rms.service.model.TargetProject;
import callum.project.uni.rms.service.repository.ProjectRepository;
import callum.project.uni.rms.service.repository.model.Project;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;

    public TargetProject getTargetProjectById(String projectId){
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if (projectOptional.isEmpty()){
            return null;
        }

        return projectMapper.mapProjectToTargetProject(projectOptional.get());
    }
}
