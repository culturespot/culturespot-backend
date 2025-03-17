package com.culturespot.culturespotdomain.core.auth.service;

import com.culturespot.culturespotdomain.core.auth.entity.RefreshToken;
import com.culturespot.culturespotdomain.core.user.entity.SocialLoginType;

public interface RefreshTokenService {
    RefreshToken saveRefreshToken(String email, SocialLoginType authType, String refreshToken);
    void deleteRefreshToken(String refreshToken);
    String generateAccessTokenFromRefreshToken(String refreshToken);
}
