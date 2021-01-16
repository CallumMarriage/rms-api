package callum.project.uni.rms.service;

import callum.project.uni.rms.model.req.ProjectCreateReq;
import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.service.mapper.ProjectMapper;
import callum.project.uni.rms.service.model.response.TargetProject;
import callum.project.uni.rms.service.repository.ProjectRepository;
import callum.project.uni.rms.service.repository.model.Project;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static callum.project.uni.rms.service.mapper.ProjectMapper.mapProjectToTargetProject;
import static callum.project.uni.rms.service.mapper.ProjectMapper.mapReqProjectToProject;

@Service
@Slf4j
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public TargetProject addNewProject(ProjectCreateReq projectCreateReq) throws ServiceException {

        try {

            Project newProject = mapReqProjectToProject(projectCreateReq);
            return mapProjectToTargetProject(projectRepository.save(newProject));

        } catch (RuntimeException e) {
            throw new ServiceException("Issue retrieving account");
        } catch (Exception e) {
            throw new ServiceException("Issue mapping account");
        }

    }

    public List<TargetProject> getProjectListForAccount(String accountNumber) throws ServiceException {
        try {

            List<Project> projects = projectRepository.findAllByAccountNumber(accountNumber);

            return createList(projects);
        } catch (RuntimeException e) {
            throw new ServiceException("Issue retrieving account");
        } catch (Exception e) {
            throw new ServiceException("Issue mapping account");
        }
    }


    public TargetProject getProjectById(String id) throws ServiceException {
        try {
            Optional<Project> projectOpt = projectRepository.findById(id);
            if (projectOpt.isEmpty()) {
                return null;
            }

            return mapProjectToTargetProject(projectOpt.get());
        } catch (RuntimeException e) {
            throw new ServiceException("Issue retrieving account");
        } catch (Exception e) {
            throw new ServiceException("Issue mapping account");
        }
    }

    private List<TargetProject> createList(Iterable<Project> projectsIterable) {
        return StreamSupport.stream(projectsIterable.spliterator(), true)
                .map(ProjectMapper::mapProjectToTargetProject)
                .collect(Collectors.toList());
    }
}
