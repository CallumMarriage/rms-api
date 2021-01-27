package callum.project.uni.rms.service.mapper;

import callum.project.uni.rms.model.req.RequestRole;
import callum.project.uni.rms.model.res.TargetRole;
import callum.project.uni.rms.service.repository.model.Role;
import org.springframework.stereotype.Component;

import static callum.project.uni.rms.service.mapper.MapperUtils.convertLocalDateToSqlDate;
import static callum.project.uni.rms.service.mapper.MapperUtils.convertSqlDateToLocalDate;

@Component
public class RoleMapper {

    public static TargetRole mapDynamoDBToTargetModel(Role dbRole) {
        return TargetRole.builder()
                .accountName(dbRole.getAccountName())
                .accountNumber(dbRole.getAccountNumber())
                .id(dbRole.getId())
                .projectName(dbRole.getProjectName())
                .projectCode(dbRole.getProjectCode())
                .endDate(convertSqlDateToLocalDate(dbRole.getEndDate()))
                .startDate(convertSqlDateToLocalDate(dbRole.getStartDate()))
                .roleName(dbRole.getRoleName())
                .description(dbRole.getDescription())
                .closestCapOffice(dbRole.getClosestCapOffice())
                .roleType(dbRole.getRoleType())
                .isRoleOpen(convertToBool(dbRole.getIsRoleOpen()))
                .build();
    }

    public static Role mapRequestToDbModel(RequestRole req) {
        return Role.builder()
                .roleName(req.getRoleName())
                .roleType(req.getRoleType())
                .projectCode(req.getProjectCode())
                .projectName(req.getProjectName())
                .accountNumber(req.getAccountNumber())
                .accountName(req.getAccountName())
                .description(req.getDescription())
                .isRoleOpen(convertToInt(req.isRoleOpen()))
                .startDate(convertLocalDateToSqlDate(req.getStartDate()))
                .endDate(convertLocalDateToSqlDate(req.getEndDate()))
                .build();
    }

    private static boolean convertToBool(int bool){
        return bool == 1;
    }

    private static int convertToInt(boolean value){
        if(value){
            return 1;
        } else {
            return 0;
        }
    }
}
