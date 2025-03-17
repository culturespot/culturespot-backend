package com.culturespot.culturespotdomain.core.auth.handler;

import com.culturespot.culturespotdomain.core.auth.service.RefreshTokenService;
import com.culturespot.culturespotdomain.core.user.entity.SocialLoginType;
import com.culturespot.culturespotdomain.core.global.security.token.JwtTokenManager;
import com.culturespot.culturespotdomain.core.global.utils.CookieUtils;
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

    public OAuth2AuthenticationSuccessHandler(
            @Value("${spring.jwt.refresh-expiration-time}") final int REFRESH_TOKEN_EXPIRATION,
            JwtTokenManager jwtTokenManager,
            RefreshTokenService refreshTokenService
    ) {
        this.jwtTokenManager = jwtTokenManager;
        this.REFRESH_TOKEN_EXPIRATION = REFRESH_TOKEN_EXPIRATION;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        if (!(authentication instanceof OAuth2AuthenticationToken oauthToken)) {
            return;
        }

        // ✅ 소셜 로그인 제공자 가져오기
        String registrationId = oauthToken.getAuthorizedClientRegistrationId();
        SocialLoginType socialLoginType = SocialLoginType.fromRegistrationId(registrationId);

        // ✅ 사용자 이메일 가져오기
        String email = authentication.getName();

        // ✅ JWT 토큰 발급
        String accessToken = jwtTokenManager.createAccessToken(email, Set.of("ROLE_USER"));
        String refreshToken = jwtTokenManager.createRefreshToken(email);

        // ✅ Access Token을 헤더에 추가
        response.setHeader("Authorization", "Bearer " + accessToken);
        System.out.println("accessToken..................................." + accessToken);

        // ✅ Refresh Token을 HttpOnly & Secure 쿠키에 저장
        Cookie refreshTokenCookie = CookieUtils.createSecureCookie(
                "refreshToken",
                refreshToken,
                REFRESH_TOKEN_EXPIRATION
        );
        response.addCookie(refreshTokenCookie);

        // ✅ refreshToken을 데이터베이스에 저장 (SocialLoginType 포함)
        refreshTokenService.saveRefreshToken(email, socialLoginType, refreshToken);

        // ✅ 리다이렉트
        String redirectUri = request.getParameter("redirect_uri");
        if (redirectUri == null || !(redirectUri.startsWith("http://localhost:3000") || redirectUri.startsWith("http://localhost:8080"))) {
            // 📍프론트에서 redirect_uri가 의도하는 대로 동작하지 않으면 수정이 필요합니다.
            redirectUri = "http://localhost:8080/api/public/test";
        }

        if (!response.isCommitted()) {
            response.sendRedirect(redirectUri);
        }
    }

}
