package callum.project.uni.rms.service.mapper;

import callum.project.uni.rms.model.req.UserCreateReq;
import callum.project.uni.rms.model.res.TargetUser;
import callum.project.uni.rms.service.repository.model.User;

public class UserMapper {

    public static TargetUser mapDbModelToTarget(User user){

        return TargetUser.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .currentRoleId(user.getCurrentRoleId())
                .userType(user.getUserType())
                .closestCapOffice(user.getClosestCapOffice())
                .resourceManagerId(user.getResourceManagerId())
                .userSpecialism(user.getUserSpecialism())
                .build();
    }

    public static User mapCreateReqToDbModel(UserCreateReq createReq){
        return User.builder()
                .ssoId(createReq.getSsoId())
                .fullName(createReq.getName())
                .currentRoleId(createReq.getCurrentRoleId())
                .userType(createReq.getUserType())
                .userSpecialism(createReq.getUserSpecialism())
                .build();
    }
}
