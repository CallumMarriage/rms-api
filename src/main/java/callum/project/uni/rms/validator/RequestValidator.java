package callum.project.uni.rms.validator;

import callum.project.uni.rms.model.req.UserCreateReq;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@NoArgsConstructor
public class RequestValidator {

    public boolean validateUserCreateRequest(UserCreateReq userCreateRequest){
        return userCreateRequest.getGoogleId() == null
                || userCreateRequest.getGoogleId().isEmpty();
    }

    public boolean validateGetByIdReq(String id){
        return id ==null || id.isEmpty() || id.isBlank();
    }

    public boolean validateGetByIdReq(Long id){
        return id ==null || id == 0;
    }


    public boolean invalid(String bodyValue) {
        return bodyValue == null || bodyValue.isEmpty();
    }

    public boolean invalid(LocalDate localDate){
        return localDate == null;
    }
    public boolean invalid(Long bodyValue) {
        return bodyValue == null || bodyValue == 0;
    }
}
