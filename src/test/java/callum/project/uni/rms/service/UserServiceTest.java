package callum.project.uni.rms.service;

import callum.project.uni.rms.model.req.UserCreateReq;
import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.model.res.TargetUser;
import callum.project.uni.rms.model.res.user.UserExist;
import callum.project.uni.rms.service.repository.UserRepository;
import callum.project.uni.rms.service.repository.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;

    private static final String SSO_ID = "123703284023018121";

    private static final Long ROLE_ID = 1L;
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
        user.setSsoId(SSO_ID);
        user.setId(1L);
        user.setCurrentRoleId(ROLE_ID);

        when(userRepository.findBySsoId(any(String.class)))
                .thenReturn(Optional.of(user));
        UserExist userExist = userService.userExists(SSO_ID);
        assertTrue(userExist.isUserExist());
    }

    @Test
    void userExists_returnsFalse() throws ServiceException {
        when(userRepository.findBySsoId(any(String.class)))
                .thenReturn(Optional.empty());

        assertFalse(userService.userExists(SSO_ID).isUserExist());
    }

    @Test
    void createUser_happyPath() throws ServiceException {
        User user = new User();
        user.setSsoId(SSO_ID);
        user.setId(1L);
        user.setCurrentRoleId(ROLE_ID);

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        UserCreateReq createReq = UserCreateReq.builder()
                .ssoId(SSO_ID)
                .build();

        TargetUser targetUser = userService.createUser(createReq);
        assertAll("Assert all user params are correct",
                () -> assertEquals(1, targetUser.getId()),
                () -> assertEquals(ROLE_ID, targetUser.getCurrentRoleId()));
    }

    @Test
    void createUser_sqlException() {
        when(userRepository.save(any(User.class)))
                .thenThrow(IllegalArgumentException.class);

        assertThrows(ServiceException.class,() -> userService.createUser(new UserCreateReq()));
    }

    @AfterEach
    void tearDown() {
    }
}