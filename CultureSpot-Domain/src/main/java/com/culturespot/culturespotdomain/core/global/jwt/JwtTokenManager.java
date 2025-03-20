package com.culturespot.culturespotdomain.core.global.jwt;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class JwtTokenManager {
    private final JwtTokenGenerator jwtTokenGenerator;
    private final JwtTokenValidator jwtTokenValidator;

    public JwtTokenManager(
            JwtTokenGenerator jwtTokenGenerator,
            JwtTokenValidator jwtTokenValidator
    ) {
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.jwtTokenValidator = jwtTokenValidator;
    }

    public String createAccessToken(String email, Set<String> roles) {
        return jwtTokenGenerator.generateAccessToken(email, roles);
    }

    public String createRefreshToken(String email) {
        return jwtTokenGenerator.generateRefreshToken(email);
    }

    public boolean isValidAccessToken(String accessToken) {
        return jwtTokenValidator.validateAccessToken(accessToken);
    }

    public boolean isValidRefreshToken(String refreshToken) {
        return jwtTokenValidator.validateRefreshToken(refreshToken);
    }

    public String getEmailFromAccessToken(String token) {
        return jwtTokenValidator.getEmailFromAccessToken(token);
    }

    public String getEmailFromRefreshToken(String token) {
        return jwtTokenValidator.getEmailFromRefreshToken(token);
    }
}
