package callum.project.uni.rms.service;

import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.service.model.response.TargetUser;
import callum.project.uni.rms.service.repository.UserRepository;
import callum.project.uni.rms.service.repository.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.sql.SQLDataException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;

    private static final String GOOGLE_ID = "123703284023018121";
    @Mock
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    @DisplayName("When user repository returns a valid user, then user exists should return true")
    void userExists_returnsTrue() throws ServiceException {

        User user = new User();
        user.setGoogleId(GOOGLE_ID);
        user.setId(1L);
        user.setCurrentRoleId("1128012");

        when(userRepository.findByGoogleId(any(String.class)))
                .thenReturn(Optional.of(user));
        assertTrue(userService.userExists(GOOGLE_ID));
    }

    @Test
    void userExists_returnsFalse() throws ServiceException {
        when(userRepository.findByGoogleId(any(String.class)))
                .thenReturn(Optional.empty());

        assertFalse(userService.userExists(GOOGLE_ID));
    }

    @Test
    void createUser_happyPath() throws ServiceException {
        User user = new User();
        user.setGoogleId(GOOGLE_ID);
        user.setId(1L);
        user.setCurrentRoleId("123");

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        TargetUser targetUser = userService.createUser(GOOGLE_ID);
        assertAll("Assert all user params are correct",
                () -> assertEquals(1, targetUser.getId()),
                () -> assertEquals("123", targetUser.getCurrentRoleId()));
    }

    @Test
    void createUser_sqlException() {
        when(userRepository.save(any(User.class)))
                .thenThrow(IllegalArgumentException.class);

        assertThrows(ServiceException.class,() -> userService.createUser(GOOGLE_ID));
    }

    @AfterEach
    void tearDown() {
    }
}