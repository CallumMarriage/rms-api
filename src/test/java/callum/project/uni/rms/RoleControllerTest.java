package callum.project.uni.rms;

import callum.project.uni.rms.service.RoleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class RoleControllerTest {

    private RoleController roleController;

    private RoleService roleService;

    @BeforeEach
    void setUp() {
        roleService = mock(RoleService.class);
    }

    @Test
    @DisplayName("Test get roles happy path")
    void getRoles_happyPath(){
        
    }
}