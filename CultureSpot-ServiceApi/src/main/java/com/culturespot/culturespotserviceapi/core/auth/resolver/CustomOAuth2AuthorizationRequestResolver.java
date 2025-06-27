package com.culturespot.culturespotserviceapi.core.auth.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class CustomOAuth2AuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {

    private final OAuth2AuthorizationRequestResolver defaultResolver;

    public CustomOAuth2AuthorizationRequestResolver(OAuth2AuthorizationRequestResolver defaultResolver) {
        this.defaultResolver = defaultResolver;
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        OAuth2AuthorizationRequest authorizationRequest = defaultResolver.resolve(request);
        return customizeAuthorizationRequest(authorizationRequest, request);
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        OAuth2AuthorizationRequest authorizationRequest = defaultResolver.resolve(request, clientRegistrationId);
        return customizeAuthorizationRequest(authorizationRequest, request);
    }

    private OAuth2AuthorizationRequest customizeAuthorizationRequest(
            OAuth2AuthorizationRequest authorizationRequest,
            HttpServletRequest request) {

        if (authorizationRequest == null) {
            return null;
        }

        // ✅ 디버깅 로그
        System.out.println("=== CustomOAuth2AuthorizationRequestResolver 디버깅 ===");
        System.out.println("Original State: " + authorizationRequest.getState());

        // ✅ 클라이언트에서 전달받은 redirect_uri 파라미터 추출
        String clientRedirectUri = request.getParameter("redirect_uri");
        System.out.println("받은 redirect_uri 파라미터: " + clientRedirectUri);

        if (!StringUtils.hasText(clientRedirectUri)) {
            clientRedirectUri = "http://localhost:3000"; // 기본값
            System.out.println("redirect_uri 파라미터가 없어서 기본값 사용: " + clientRedirectUri);
        }

        // ✅ 허용된 도메인 검증 (보안을 위해)
        if (!isValidRedirectUri(clientRedirectUri)) {
            System.out.println("허용되지 않은 도메인: " + clientRedirectUri + ", 기본값으로 변경");
            clientRedirectUri = "http://localhost:3000";
        } else {
            System.out.println("허용된 도메인: " + clientRedirectUri);
        }

        // ✅ state에 redirect_uri 정보 포함 (Base64 인코딩)
        String originalState = authorizationRequest.getState();
        String encodedRedirectUri = Base64.getEncoder().encodeToString(
                clientRedirectUri.getBytes(StandardCharsets.UTF_8)
        );
        String newState = originalState + ":" + encodedRedirectUri;

        System.out.println("인코딩된 redirect_uri: " + encodedRedirectUri);
        System.out.println("새로운 State: " + newState);
        System.out.println("=== CustomOAuth2AuthorizationRequestResolver 디버깅 END ===");

        // ✅ 기존 요청을 복사하면서 state만 변경
        Map<String, Object> additionalParameters = new HashMap<>(authorizationRequest.getAdditionalParameters());

        return OAuth2AuthorizationRequest.from(authorizationRequest)
                .state(newState)
                .additionalParameters(additionalParameters)
                .build();
    }

    /**
     * ✅ 허용된 리다이렉트 URI인지 검증
     */
    private boolean isValidRedirectUri(String uri) {
        if (!StringUtils.hasText(uri)) {
            return false;
        }

        System.out.println("Resolver - 도메인 검증 중: " + uri);

        // ✅ 개발/테스트용 허용 도메인 (실제 환경에 맞게 수정)
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

        System.out.println("Resolver - 도메인 검증 결과: " + isValid);
        return isValid;
    }
}