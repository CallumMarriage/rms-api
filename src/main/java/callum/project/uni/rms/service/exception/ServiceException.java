package callum.project.uni.rms.service.exception;

import lombok.Getter;

@Getter
public class ServiceException extends Exception {

    private String message;

    public ServiceException(String message){
        this.message = message;
    }
}
