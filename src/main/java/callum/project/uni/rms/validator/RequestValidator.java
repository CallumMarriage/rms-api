package callum.project.uni.rms.validator;

import callum.project.uni.rms.model.req.UserCreateReq;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class RequestValidator {

    public Boolean validateUserCreateRequest(UserCreateReq userCreateRequest){
        return userCreateRequest.getGoogleId() == null
                || userCreateRequest.getGoogleId().isEmpty();
    }

    public Boolean validateGetByIdReq(String id){
        return id ==null || id.isEmpty() || id.isBlank();
    }
}
