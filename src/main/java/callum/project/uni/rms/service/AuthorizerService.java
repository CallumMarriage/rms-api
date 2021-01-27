package callum.project.uni.rms.service;

import callum.project.uni.rms.common.UserType;
import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.service.repository.RoleApplicationRepository;
import callum.project.uni.rms.service.repository.UserRepository;
import callum.project.uni.rms.service.repository.model.RoleApplication;
import callum.project.uni.rms.service.repository.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class AuthorizerService {

    private final UserRepository userRepository;

    private final RoleApplicationRepository roleApplicationRepository;

    public boolean userAuthorizedToConfirmApplication(Long authId, Long applicantId) throws ServiceException {

        try {
            log.info("Checking if user is authorized to update this resource");
            Optional<User> user = userRepository.findById(authId);

            if (user.isEmpty()) {
                return false;
            }

            User manager = user.get();

            if (!manager.getUserType().equals(UserType.RESOURCE_MANAGER) &&
                    !manager.getUserType().equals(UserType.PROJECT_MANAGER)) {
                log.error("User is not authorized to access this resource.");
                return false;
            }

            Optional<User> applicantOpt = userRepository.findById(applicantId);

            if (applicantOpt.isEmpty()) {
                return false;
            }

            User applicant = applicantOpt.get();
            
            return applicant.getResourceManagerId()
                    .equals(manager.getId());
        } catch (RuntimeException e){
            log.error(e.getLocalizedMessage());
            throw new ServiceException("Issue authorizing resource manager");
        }
    }
}
