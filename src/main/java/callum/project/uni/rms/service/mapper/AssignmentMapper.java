package callum.project.uni.rms.service.mapper;

import callum.project.uni.rms.service.repository.model.Assignment;

import java.sql.Date;
import java.time.LocalDate;

import static callum.project.uni.rms.service.mapper.MapperUtils.convertLocalDateToSqlDate;

public class AssignmentMapper {

    public static Assignment buildAssignmentFromSql( Long userId, Long roleId, Date startDate, Date endDate){
        return buildAssignment(userId, roleId, startDate, endDate);
    }

    public static Assignment buildAssignmentFromTarget(Long userId, Long roleId, LocalDate startDate, LocalDate endDate){
        return buildAssignment(userId,
                roleId,
                convertLocalDateToSqlDate(startDate),
                convertLocalDateToSqlDate(endDate));
    }

    private static Assignment buildAssignment( Long userId, Long roleId, Date startDate, Date endDate){
        return Assignment.builder()
                .startDate(startDate)
                .endDate(endDate)
                .roleId(roleId)
                .userId(userId)
                .build();
    }
}
