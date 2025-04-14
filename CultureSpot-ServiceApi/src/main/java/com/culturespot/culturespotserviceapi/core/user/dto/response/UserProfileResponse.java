package com.culturespot.culturespotserviceapi.core.user.dto.response;

import com.culturespot.culturespotdomain.core.user.entity.SocialLoginType;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

@JsonRootName("user")
public record UserProfileResponse(
    Long userId,
    String username,
    SocialLoginType platform,
    String email,
    int profileCode,
    List<String> preferredGenres
){
}
