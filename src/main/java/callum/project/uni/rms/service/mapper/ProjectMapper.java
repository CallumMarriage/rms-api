package callum.project.uni.rms.service.mapper;

import callum.project.uni.rms.service.model.response.TargetProject;
import callum.project.uni.rms.service.repository.model.Project;
import org.springframework.stereotype.Component;

import static callum.project.uni.rms.service.mapper.MapperUtils.convertSqlDateToLocalDate;

@Component
public class ProjectMapper {


    public static TargetProject mapProjectToTargetProject(Project dbProject){
        
        return TargetProject.builder()
                .projectCode(dbProject.getProjectCode())
                .projectName(dbProject.getProjectName())
                .endDate(convertSqlDateToLocalDate(dbProject.getEndDate()))
                .startDate(convertSqlDateToLocalDate(dbProject.getStartDate()))
                .description(dbProject.getDescription())
                .build();
    }

}
