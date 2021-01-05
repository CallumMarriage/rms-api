package callum.project.uni.rms.service.mapper;

import callum.project.uni.rms.service.model.response.TargetUser;
import callum.project.uni.rms.service.repository.model.User;

public class UserMapper {

    public static TargetUser mapDbModelToTarget(User user){

        return TargetUser.builder()
                .id(user.getId())
                .currentRoleId(user.getCurrentRoleId())
                .build();
    }
}
