package com.culturespot.culturespotserviceapi.core.auth.resolver;

import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;

import java.util.HashMap;
import java.util.Map;

/*
*      [출처 : chatGPT]
*       파일 생성 이유
*        - http://localhost:8080/oauth2/authorization/google 링크로 구글 로그인을 요청하면
*        - 사용자에게 어떤 계정을 선택할 것인지, 사용자 정보 제공 동의를 묻지 않고 회원가입 및 access-token, refresh-token이 발급되는 상황이 연출되어짐
*        - 이 파일을 생성하고 SecurityFilterChain에 추가함으로서 어떤 계정을 선택할 것인지, 사용자 정보 제공 동의를 묻는 화면이 나오도록 조치
*
*       문제점
*        - 의도한대로 동작은 하나, 동작의 이유를 잘 모르겠음
* */

public class CustomOAuth2AuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {

    private final DefaultOAuth2AuthorizationRequestResolver defaultResolver;

    public CustomOAuth2AuthorizationRequestResolver(DefaultOAuth2AuthorizationRequestResolver defaultResolver) {
        this.defaultResolver = defaultResolver;
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        OAuth2AuthorizationRequest authorizationRequest = defaultResolver.resolve(request);
        return modifyAuthorizationRequest(authorizationRequest);
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        OAuth2AuthorizationRequest authorizationRequest = defaultResolver.resolve(request, clientRegistrationId);
        return modifyAuthorizationRequest(authorizationRequest);
    }

    private OAuth2AuthorizationRequest modifyAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest) {
        if (authorizationRequest == null) {
            return null;
        }

        Map<String, Object> additionalParameters = new HashMap<>(authorizationRequest.getAdditionalParameters());

        String registrationId = (String) authorizationRequest.getAttribute(OAuth2ParameterNames.REGISTRATION_ID);

        // Google 계정 선택을 강제하는 `prompt=consent select_account`
        if ("google".equals(registrationId)) {
            additionalParameters.put("prompt", "consent select_account");
        }

        // Kakao 계정 선택을 강제하는 `prompt=login`
        if ("kakao".equals(registrationId)) {
            additionalParameters.put("prompt", "login");
        }

        return OAuth2AuthorizationRequest.from(authorizationRequest)
                .additionalParameters(additionalParameters)
                .build();
    }

}

