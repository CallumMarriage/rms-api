package callum.project.uni.rms.service;

import callum.project.uni.rms.model.req.UserCreateReq;
import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.service.mapper.UserMapper;
import callum.project.uni.rms.model.res.TargetUser;
import callum.project.uni.rms.model.res.user.Candidates;
import callum.project.uni.rms.model.res.user.UserExist;
import callum.project.uni.rms.service.repository.UserRepository;
import callum.project.uni.rms.service.repository.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static callum.project.uni.rms.service.mapper.UserMapper.mapCreateReqToDbModel;

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

    public Candidates retrieveUserByFullName(String fullName) throws ServiceException {
        try {
            List<User> candidates = userRepository.findAllByFullName(fullName);

            return Candidates.builder()
                    .candidates(candidates.parallelStream()
                            .map(UserMapper::mapDbModelToTarget)
                            .collect(Collectors.toList()))
                    .build();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ServiceException("Error adding user");
        }
    }

    public TargetUser createUser(UserCreateReq createReq) throws ServiceException {

        User user = mapCreateReqToDbModel(createReq);
        try {
            User response = userRepository.save(user);
            return UserMapper.mapDbModelToTarget(response);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ServiceException("Error adding user");
        }
    }

    public UserExist userExists(String ssoId) throws ServiceException {
        try {

            Optional<User> user = userRepository.findBySsoId(ssoId);

            return UserExist.builder()
                    .userExist(user.isPresent())
                    .build();

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

    public Candidates retrieveUserByRmId(Long rmId) throws ServiceException {
        try {

            List<User> users = userRepository.findAllByResourceManagerId(rmId);

            return Candidates.builder()
                    .candidates(users.parallelStream()
                            .map(UserMapper::mapDbModelToTarget)
                            .collect(Collectors.toList()))
                    .build();

        } catch (RuntimeException e) {
            log.error(e.getLocalizedMessage());
            throw new ServiceException("Error finding user");
        }
    }

    public TargetUser retrieveUserDetailsBySsoId(String ssoId) throws ServiceException {
        try {

            Optional<User> user = userRepository.findBySsoId(ssoId);

            if (user.isEmpty()) {
                throw new ServiceException("Expected user not found");
            }

            return UserMapper.mapDbModelToTarget(user.get());

        } catch (RuntimeException e) {
            throw new ServiceException("Error finding user");
        }
    }
}
