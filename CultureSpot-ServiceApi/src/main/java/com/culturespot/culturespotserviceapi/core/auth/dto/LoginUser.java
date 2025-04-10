package com.culturespot.culturespotserviceapi.core.auth.dto;

import com.culturespot.culturespotdomain.core.user.entity.SocialLoginType;

public record LoginUser(
        Long userId,
        String username,
        SocialLoginType platform,
        String email,
        int profileCode
) {}

