package com.culturespot.culturespotserviceapi.core.user.dto;

import com.culturespot.culturespotdomain.core.user.entity.SocialLoginType;

import java.util.List;

public record UserProfile (
        Long userId,
        String username,
        SocialLoginType platform,
        String email,
        int profileCode,
        List<String> preferredGenres
){
}
