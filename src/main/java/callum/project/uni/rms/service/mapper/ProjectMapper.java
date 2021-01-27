package callum.project.uni.rms.service.mapper;

import callum.project.uni.rms.model.req.ProjectCreateReq;
import callum.project.uni.rms.model.res.TargetProject;
import callum.project.uni.rms.service.repository.model.Project;
import org.springframework.stereotype.Component;

import static callum.project.uni.rms.service.mapper.MapperUtils.convertLocalDateToSqlDate;
import static callum.project.uni.rms.service.mapper.MapperUtils.convertSqlDateToLocalDate;

@Component
public class ProjectMapper {


    public static TargetProject mapProjectToTargetProject(Project dbProject) {

        return TargetProject.builder()
                .projectCode(dbProject.getProjectCode())
                .accountNumber(dbProject.getAccountNumber())
                .projectName(dbProject.getProjectName())
                .endDate(convertSqlDateToLocalDate(dbProject.getEndDate()))
                .startDate(convertSqlDateToLocalDate(dbProject.getStartDate()))
                .description(dbProject.getDescription())
                .build();
    }

    public static Project mapReqProjectToProject(ProjectCreateReq project) {

        return Project.builder()
                .accountNumber(project.getAccountNumber())
                .description(project.getDescription())
                .startDate(convertLocalDateToSqlDate(project.getStartDate()))
                .endDate(convertLocalDateToSqlDate(project.getEndDate()))
                .projectCode(project.getProjectCode())
                .projectName(project.getProjectName())
                .projectManagerId(project.getProjectManagerId())
                .build();
    }

}
