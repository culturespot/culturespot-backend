package com.culturespot.culturespotdomain.core.refreshToken.service;

import com.culturespot.culturespotdomain.core.global.jwt.JwtTokenManager;
import com.culturespot.culturespotdomain.core.refreshToken.entity.RefreshToken;
import com.culturespot.culturespotdomain.core.refreshToken.repository.RefreshTokenRepository;
import com.culturespot.culturespotdomain.core.user.entity.SocialLoginType;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotdomain.core.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@AllArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final JwtTokenManager jwtTokenManager;
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public RefreshToken saveRefreshToken(String userEmail, SocialLoginType authType, String refreshToken) {
        User user = userService.createUserIfNotExists(userEmail, authType);
        refreshTokenRepository.deleteByUser(user);
        return refreshTokenRepository.save(buildRefreshToken(user, refreshToken));
    }

    @Override
    @Transactional
    public void deleteRefreshToken(String refreshToken) {
        String userEmail = jwtTokenManager.getEmailFromRefreshToken(refreshToken);
        User user = userService.findUserOrThrow(userEmail);
        refreshTokenRepository.deleteByUser(user);
    }

    @Override
    public String generateAccessTokenFromRefreshToken(String refreshToken) {
        jwtTokenManager.isValidRefreshToken(refreshToken);

        String userEmail = jwtTokenManager.getEmailFromRefreshToken(refreshToken);
        User user = userService.findUserOrThrow(userEmail);
        Set<String> userRoles = userService.getRoleNames(user);

        return jwtTokenManager.createAccessToken(user.getEmail(), userRoles);
    }

    public RefreshToken buildRefreshToken(User user, String refreshToken) {
        return RefreshToken.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();
    }
}
