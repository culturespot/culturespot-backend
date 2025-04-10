package com.culturespot.culturespotserviceapi.core.auth.handler;

import com.culturespot.culturespotdomain.core.refreshToken.service.RefreshTokenService;
import com.culturespot.culturespotdomain.core.user.entity.SocialLoginType;
import com.culturespot.culturespotdomain.core.global.jwt.JwtTokenManager;
import com.culturespot.culturespotserviceapi.core.auth.dto.response.LoginSuccessResponse;
import com.culturespot.culturespotserviceapi.core.auth.strategy.OAuth2LoginSuccessHandler;
import com.culturespot.culturespotserviceapi.core.global.utils.CookieUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final int REFRESH_TOKEN_EXPIRATION;
    private final JwtTokenManager jwtTokenManager;
    private final RefreshTokenService refreshTokenService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    public OAuth2AuthenticationSuccessHandler(
            @Value("${spring.jwt.refresh-expiration-time}") final int REFRESH_TOKEN_EXPIRATION,
            JwtTokenManager jwtTokenManager,
            RefreshTokenService refreshTokenService,
            OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler
    ) {
        this.jwtTokenManager = jwtTokenManager;
        this.REFRESH_TOKEN_EXPIRATION = REFRESH_TOKEN_EXPIRATION;
        this.refreshTokenService = refreshTokenService;
        this.oAuth2LoginSuccessHandler = oAuth2LoginSuccessHandler;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        if (!(authentication instanceof OAuth2AuthenticationToken oauthToken)) return;

        String registrationId = oauthToken.getAuthorizedClientRegistrationId(); // 소셜 로그인 제공자 가져오기
        String email = authentication.getName(); // 사용자 이메일 가져오기

        // ✅ JWT 토큰 발급
        String accessToken = jwtTokenManager.createAccessToken(email, Set.of("ROLE_USER"));
        String refreshToken = jwtTokenManager.createRefreshToken(email);

        response.setHeader("Authorization", "Bearer " + accessToken); // access token 헤더에 추가

        // ✅ Refresh Token을 HttpOnly & Secure 쿠키에 저장
        Cookie refreshTokenCookie = CookieUtils.createSecureCookie(
                "refreshToken", refreshToken, REFRESH_TOKEN_EXPIRATION
        );
        response.addCookie(refreshTokenCookie);

        // ✅ refreshToken db 저장 (SocialLoginType 포함)
        refreshTokenService.saveRefreshToken(email,  SocialLoginType.fromRegistrationId(registrationId), refreshToken);

        // ✅ JSON 응답 설정
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // ✅ 응답 객체 생성 & 사용자 최신 로그인 시간 업데이트
        LoginSuccessResponse responseDto = oAuth2LoginSuccessHandler.handle(registrationId, email);

        new ObjectMapper()
                .writeValue(response.getWriter(), responseDto);
    }
}
