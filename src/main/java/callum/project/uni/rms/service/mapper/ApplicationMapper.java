package callum.project.uni.rms.service.mapper;

import callum.project.uni.rms.service.model.response.TargetApplication;
import callum.project.uni.rms.service.repository.model.RoleApplication;
import org.springframework.stereotype.Component;

import static callum.project.uni.rms.service.mapper.MapperUtils.convertSqlDateToLocalDate;

@Component
public class ApplicationMapper {

    public static TargetApplication mapAppToTargetApp(RoleApplication app){

        return TargetApplication.builder()
                .accountId(app.getAccountId())
                .projectId(app.getProjectId())
                .roleId(app.getRoleId())
                .applicationId(app.getApplicationId())
                .applicationDate(convertSqlDateToLocalDate(app.getApplicationDate()))
                .applicationStatus(app.getApplicationStatus())
                .lastUpdatedDate(convertSqlDateToLocalDate(app.getLastUpdatedDate()))
                .build();
    }
}
