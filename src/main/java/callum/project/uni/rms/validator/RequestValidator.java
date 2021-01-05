package callum.project.uni.rms.validator;

import callum.project.uni.rms.service.model.request.UserCreateRequest;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class RequestValidator {

    public Boolean validateUserCreateRequest(UserCreateRequest userCreateRequest){
        return !userCreateRequest.getGoogleId().isEmpty();
    }

    public Boolean validateGetByIdReq(String id){
        return id.isEmpty() && id.isBlank();
    }
}
