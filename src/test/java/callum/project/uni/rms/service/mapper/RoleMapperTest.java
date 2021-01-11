package callum.project.uni.rms.service.mapper;

import callum.project.uni.rms.service.repository.model.Role;
import callum.project.uni.rms.service.model.response.TargetRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleMapperTest {

    @Test
    @DisplayName("Test basic mapping")
    void basicMap(){
        Role role = new Role();
        role.setAccountNumber("18023830");
        role.setProjectCode("8302");
        role.setId("2710380");
        role.setEndDate(Date.valueOf("2021-12-21"));
        role.setStartDate(Date.valueOf("2021-12-21"));

        TargetRole mappedRole = RoleMapper.mapDynamoDBToTargetModel(role);
        LocalDate expectedEndDateTime = LocalDate.of(2021, 12, 21);
        assertEquals(expectedEndDateTime, mappedRole.getEndDate());
        assertEquals(expectedEndDateTime, mappedRole.getStartDate());
        assertEquals("18023830", mappedRole.getAccountNumber());
        assertEquals("8302", mappedRole.getProjectCode());
        assertEquals("2710380", mappedRole.getId());
    }
}
