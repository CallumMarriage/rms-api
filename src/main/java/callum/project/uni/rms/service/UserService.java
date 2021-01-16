package callum.project.uni.rms.service;

import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.service.mapper.UserMapper;
import callum.project.uni.rms.service.model.response.TargetUser;
import callum.project.uni.rms.service.repository.UserRepository;
import callum.project.uni.rms.service.repository.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void updateUserProjectDetails(Long userId, Long roleId)
            throws ServiceException {
        try {
            userRepository.updateUserCurrentRoleId(userId, roleId);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ServiceException("Error updating user");
        }
    }

    public TargetUser createUser(String googleId) throws ServiceException {

        User user = new User();
        user.setGoogleId(googleId);
        try {
            User response = userRepository.save(user);
            return UserMapper.mapDbModelToTarget(response);
        } catch (RuntimeException e) {
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

    public TargetUser retrieveUserByInternalId(Long userId) throws ServiceException {
        try {

            Optional<User> user = userRepository.findById(userId);

            if (user.isEmpty()) {
                throw new ServiceException("Expected user not found");
            }

            return UserMapper.mapDbModelToTarget(user.get());

        } catch (RuntimeException e) {
            throw new ServiceException("Error finding user");
        }
    }

    public TargetUser retrieveUserDetailsByGoogleId(String googleId) throws ServiceException {
        try {

            log.info("Google ID for user request {}", googleId);
            Optional<User> user = userRepository.findByGoogleId(googleId);

            if (user.isEmpty()) {
                throw new ServiceException("Expected user not found");
            }

            return UserMapper.mapDbModelToTarget(user.get());

        } catch (RuntimeException e) {
            throw new ServiceException("Error finding user");
        }
    }
}
