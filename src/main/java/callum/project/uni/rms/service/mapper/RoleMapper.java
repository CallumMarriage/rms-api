package callum.project.uni.rms.service.mapper;

import callum.project.uni.rms.service.model.response.TargetRole;
import callum.project.uni.rms.service.repository.model.Role;
import org.springframework.stereotype.Component;

import static callum.project.uni.rms.service.mapper.MapperUtils.convertSqlDateToLocalDate;

@Component
public class RoleMapper {

    public static TargetRole mapDynamoDBToTargetModel(Role dbRole){
        return TargetRole.builder()
                .accountNumber(dbRole.getAccountNumber())
                .id(dbRole.getId())
                .projectCode(dbRole.getProjectCode())
                .endDate(convertSqlDateToLocalDate(dbRole.getEndDate()))
                .startDate(convertSqlDateToLocalDate(dbRole.getStartDate()))
                .roleName(dbRole.getRoleName())
                .description(dbRole.getDescription())
                .build();
    }

}
