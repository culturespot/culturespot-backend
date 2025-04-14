package com.culturespot.culturespotserviceapi.core.user.mapper;

import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.core.user.dto.request.UserProfileRequest;
import com.culturespot.culturespotserviceapi.core.user.dto.response.UserProfileResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.ArrayList;
import java.util.Arrays;

@Mapper(componentModel = "spring",
        imports = {
        Arrays.class,
        ArrayList.class
})
public interface UserMapper {

    @Mappings ({
        @Mapping(source = "id", target = "userId"),
        @Mapping(source = "nickname", target = "username"),
        @Mapping(source = "authType", target = "platform"),
        @Mapping(source = "email", target = "email"),
        @Mapping(source = "profileCode", target = "profileCode"),
        @Mapping(
                target = "preferredGenres",
                expression = "java(new ArrayList<>(user.getPreferredCategory().values()))"
        )
    })
    UserProfileResponse userToUserProfileResponse(User user);

    @Mappings ({
            @Mapping(source = "username", target = "nickname"),
            @Mapping(source = "profileCode", target = "profileCode")
    })
    void applyUserProfileRequestPartially(
            UserProfileRequest request,
            @MappingTarget User user
    );

}