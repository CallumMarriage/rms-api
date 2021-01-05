package callum.project.uni.rms.service;

import callum.project.uni.rms.service.model.response.FullApplicationInfo;
import callum.project.uni.rms.service.model.response.TargetApplication;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class RoleApplicationService {

    /**
     * Find full information for an application. Includes User, Project and Account info for that application.
     * @return project, account, user and application info.
     */
    public FullApplicationInfo retrieveApplicationInfoForId(String applicationId){
        return new FullApplicationInfo();
    }

    /**
     * Retrieve particular application based on it's id. Only returns info pertaining to the application itself.
     * @param applicationId application id
     * @return application info in target format.
     */
    public TargetApplication retrieveApplicationById(String applicationId){
        return new TargetApplication(null, null, null);
    }

    /**
     * Retrieve all of the applications for a user.
     * @param userId the internal user id.
     * @return the basic info for all of the applications a user has.
     */
    public List<TargetApplication> retrieveAllApplicationsForUser(String userId){
        return Collections.singletonList(new TargetApplication(null, null, null));
    }
}
