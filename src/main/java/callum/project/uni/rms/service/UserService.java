package callum.project.uni.rms.service;

import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.service.mapper.UserMapper;
import callum.project.uni.rms.service.model.response.TargetUser;
import callum.project.uni.rms.service.repository.UserRepository;
import callum.project.uni.rms.service.repository.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public TargetUser createUser(String googleId) throws ServiceException {
        User user = User.builder()
                .googleId(googleId)
                .build();

        try {
            return UserMapper.mapDbModelToTarget(userRepository.save(user));

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException("Error adding user");
        }
    }

    public boolean userExists(String googleId) throws ServiceException {
        try {

            Optional<User> user = userRepository.findByGoogleId(googleId);
            return user.isPresent();
        } catch (Exception e) {
            throw new ServiceException("Error finding user");
        }
    }

    public TargetUser retrieveUserDetails(String googleId) throws ServiceException {
        try {

            Optional<User> user = userRepository.findByGoogleId(googleId);

            return UserMapper.mapDbModelToTarget(
                    user.orElseThrow(() -> new ServiceException("Expected user not found"))
            );

        } catch (RuntimeException e) {
            throw new ServiceException("Error finding user");
        }
    }
}
