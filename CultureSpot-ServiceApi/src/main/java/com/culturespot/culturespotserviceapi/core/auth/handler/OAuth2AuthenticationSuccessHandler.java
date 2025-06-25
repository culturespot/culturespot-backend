package com.culturespot.culturespotserviceapi.core.auth.handler;

import com.culturespot.culturespotdomain.core.refreshToken.service.RefreshTokenService;
import com.culturespot.culturespotdomain.core.user.entity.SocialLoginType;
import com.culturespot.culturespotdomain.core.global.jwt.JwtTokenManager;
import com.culturespot.culturespotserviceapi.common.utils.CookieUtils;
import com.culturespot.culturespotserviceapi.core.auth.dto.response.LoginSuccessResponse;
import com.culturespot.culturespotserviceapi.core.auth.strategy.OAuth2LoginSuccessHandler;
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

        // === 디버깅 추가 ===
        System.out.println("=== OAuth2 인증 성공 디버깅 ===");
        Cookie[] cookies = request.getCookies();
        System.out.println("받은 쿠키 개수: " + (cookies != null ? cookies.length : 0));
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println("쿠키: " + cookie.getName() + " = " + cookie.getValue());
            }
        }

        if (!(authentication instanceof OAuth2AuthenticationToken oauthToken)) return;

        String registrationId = oauthToken.getAuthorizedClientRegistrationId();
        String email = authentication.getName();

        String accessToken = jwtTokenManager.createAccessToken(email, Set.of("ROLE_USER"));
        String refreshToken = jwtTokenManager.createRefreshToken(email);

        response.setHeader("Authorization", "Bearer " + accessToken);

        // ✅ 새로운 메소드 사용
        CookieUtils.addSecureCookieToResponse(response, "refreshToken", refreshToken, REFRESH_TOKEN_EXPIRATION);

        refreshTokenService.saveRefreshToken(email, SocialLoginType.fromRegistrationId(registrationId), refreshToken);

        LoginSuccessResponse responseDto = oAuth2LoginSuccessHandler.handle(registrationId, email);

        String targetUrl = determineTargetUrl(request);
        System.out.println("최종 리다이렉트 URL: " + targetUrl);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request) {
        String redirectUri = CookieUtils.getCookie(request, "redirect_uri")
                .map(Cookie::getValue)
                .orElse("http://localhost:3000");

        // 로그 추가
        System.out.println("쿠키에서 받아온 소셜로그인 리다이렉트 URI: " + redirectUri);

        return redirectUri;

    }
}
