package callum.project.uni.rms.service.mapper;

import java.sql.Date;
import java.time.LocalDate;

public class MapperUtils {

    public static LocalDate convertSqlDateToLocalDate(Date sqlDate){
        return sqlDate == null ? null : sqlDate.toLocalDate();
    }
}
