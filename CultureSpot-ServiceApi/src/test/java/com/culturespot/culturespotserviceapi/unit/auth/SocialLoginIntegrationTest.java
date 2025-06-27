package com.culturespot.culturespotserviceapi.unit.auth;

import com.culturespot.culturespotdomain.core.global.jwt.JwtTokenManager;
import com.culturespot.culturespotdomain.core.refreshToken.service.RefreshTokenService;
import com.culturespot.culturespotserviceapi.core.auth.strategy.OAuth2LoginSuccessHandler;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JwtTokenManager.class)
@AutoConfigureMockMvc
class SocialLoginIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtTokenManager jwtTokenManager;

    @MockBean
    private RefreshTokenService refreshTokenService;

    @MockBean
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Test
    void 소셜로그인_플로우전체_테스트() throws Exception {
        // Given: Mock JWT 토큰 생성
        when(jwtTokenManager.createAccessToken(anyString(), anySet())).thenReturn("dummyAccessToken");
        when(jwtTokenManager.createRefreshToken(anyString())).thenReturn("dummyRefreshToken");

        // When: MockMvc를 사용하여 소셜 로그인 엔드포인트 호출
        mockMvc.perform(get("/oauth2/authorization/kakao")
                .cookie(new Cookie("redirect_uri", "http://localhost:3000")))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("http://localhost:3000")); // 리다이렉트 URL 검증
    }
}