package callum.project.uni.rms.service.mapper;


import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MapperUtilsTest {

    @Test
    void nullDate(){
        assertNull(MapperUtils.convertSqlDateToLocalDate(null));
    }

    @Test
    void nonNullDate(){
        LocalDate localDate = LocalDate.now();
        Date sqlDate = Date.valueOf(localDate);
        assertEquals(localDate, MapperUtils.convertSqlDateToLocalDate(sqlDate));
    }
}
