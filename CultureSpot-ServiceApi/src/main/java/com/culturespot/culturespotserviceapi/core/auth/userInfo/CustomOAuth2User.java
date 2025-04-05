package com.culturespot.culturespotserviceapi.core.auth.userInfo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final OAuth2User oAuth2User;
    private final String email; // principalName으로 사용
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomOAuth2User(OAuth2User oAuth2User, String email, Collection<? extends GrantedAuthority> authorities) {
        this.oAuth2User = oAuth2User;
        this.email = email;
        this.authorities = authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        // principalName이 null 또는 빈 문자열이면 Spring Security에서 예외 발생
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("OAuth2User의 principalName(email)이 비어있습니다.");
        }
        return email;
    }
}
