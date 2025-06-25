package com.culturespot.culturespotserviceapi.core.auth.handler;

import com.culturespot.culturespotdomain.core.refreshToken.service.RefreshTokenService;
import com.culturespot.culturespotdomain.core.user.entity.SocialLoginType;
import com.culturespot.culturespotdomain.core.global.jwt.JwtTokenManager;
import com.culturespot.culturespotserviceapi.common.utils.CookieUtils;
import com.culturespot.culturespotserviceapi.core.auth.dto.response.LoginSuccessResponse;
import com.culturespot.culturespotserviceapi.core.auth.strategy.OAuth2LoginSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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

        // ✅ 디버깅 정보 출력
        System.out.println("=== OAuth2 인증 성공 디버깅 START ===");
        System.out.println("Request URL: " + request.getRequestURL());
        System.out.println("Request Method: " + request.getMethod());
        System.out.println("Request URI: " + request.getRequestURI());

        // 모든 파라미터 출력
        System.out.println("Request Parameters:");
        request.getParameterMap().forEach((key, values) -> {
            System.out.println("  " + key + ": " + String.join(", ", values));
        });

        if (!(authentication instanceof OAuth2AuthenticationToken oauthToken)) {
            System.out.println("Authentication이 OAuth2AuthenticationToken이 아닙니다.");
            return;
        }

        String registrationId = oauthToken.getAuthorizedClientRegistrationId(); // 소셜 로그인 제공자 가져오기
        String email = authentication.getName(); // 사용자 이메일 가져오기

        System.out.println("Registration ID: " + registrationId);
        System.out.println("User Email: " + email);

        // ✅ JWT 토큰 발급
        String accessToken = jwtTokenManager.createAccessToken(email, Set.of("ROLE_USER"));
        String refreshToken = jwtTokenManager.createRefreshToken(email);

        response.setHeader("Authorization", "Bearer " + accessToken); // access token 헤더에 추가

        // ✅ Refresh Token을 HttpOnly & Secure 쿠키에 저장 (수정된 메소드 사용)
        CookieUtils.addSecureCookieToResponse(response, "refreshToken", refreshToken, REFRESH_TOKEN_EXPIRATION);

        // ✅ refreshToken db 저장 (SocialLoginType 포함)
        refreshTokenService.saveRefreshToken(email, SocialLoginType.fromRegistrationId(registrationId), refreshToken);

        // ✅ 최신 로그인 시간 업데이트
        LoginSuccessResponse responseDto = oAuth2LoginSuccessHandler.handle(registrationId, email);

        // ✅ state에서 redirect_uri 추출하여 최종 리다이렉트 URL 결정
        String targetUrl = determineTargetUrl(request);

        System.out.println("최종 리다이렉트 URL: " + targetUrl);
        System.out.println("=== OAuth2 인증 성공 디버깅 END ===");

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request) {
        String redirectUri = "http://localhost:3000"; // 기본값

        // ✅ state 파라미터에서 redirect_uri 추출
        String state = request.getParameter("state");
        System.out.println("받은 state 파라미터: " + state);

        if (StringUtils.hasText(state) && state.contains(":")) {
            try {
                String[] stateParts = state.split(":", 2);
                if (stateParts.length == 2) {
                    byte[] decodedBytes = Base64.getDecoder().decode(stateParts[1]);
                    String decodedRedirectUri = new String(decodedBytes, StandardCharsets.UTF_8);
                    System.out.println("디코딩된 redirect_uri: " + decodedRedirectUri);

                    // ✅ 허용된 도메인 검증
                    if (isValidRedirectUri(decodedRedirectUri)) {
                        redirectUri = decodedRedirectUri;
                        System.out.println("검증 통과: " + redirectUri);
                    } else {
                        System.out.println("허용되지 않은 도메인: " + decodedRedirectUri + ", 기본값 사용");
                    }
                }
            } catch (Exception e) {
                System.err.println("State 파라미터 파싱 오류: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("State 파라미터가 없거나 올바르지 않음, 기본값 사용");
        }

        // ✅ 쿠키 방식도 fallback으로 유지 (호환성을 위해)
        if (redirectUri.equals("http://localhost:3000")) {
            String cookieRedirectUri = CookieUtils.getCookie(request, "redirect_uri")
                    .map(cookie -> cookie.getValue())
                    .orElse(null);

            System.out.println("쿠키에서 받아온 redirect_uri: " + cookieRedirectUri);

            if (StringUtils.hasText(cookieRedirectUri) && isValidRedirectUri(cookieRedirectUri)) {
                redirectUri = cookieRedirectUri;
                System.out.println("쿠키 방식으로 redirect_uri 설정: " + redirectUri);
            }
        }

        System.out.println("최종 결정된 redirect_uri: " + redirectUri);
        return redirectUri;
    }

    /**
     * ✅ 허용된 리다이렉트 URI인지 검증
     */
    private boolean isValidRedirectUri(String uri) {
        if (!StringUtils.hasText(uri)) {
            return false;
        }

        System.out.println("Handler - 도메인 검증 중: " + uri);

        // ✅ 허용된 도메인 목록 (실제 환경에 맞게 수정)
        boolean isValid = uri.startsWith("http://localhost:3000") ||
                uri.startsWith("https://localhost:3000") ||
                uri.startsWith("http://127.0.0.1:3000") ||
                uri.startsWith("https://yourdomain.com") ||
                uri.startsWith("https://www.yourdomain.com") ||
                uri.startsWith("http://naver.com") ||
                uri.startsWith("https://naver.com") ||
                // ✅ 테스트용 도메인들 허용
                uri.startsWith("http://testssssdfa.com") ||
                uri.startsWith("http://test");  // test로 시작하는 모든 도메인

        System.out.println("Handler - 도메인 검증 결과: " + isValid);
        return isValid;
    }
}