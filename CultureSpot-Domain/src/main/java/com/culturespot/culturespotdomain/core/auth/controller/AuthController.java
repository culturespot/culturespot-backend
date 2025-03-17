package com.culturespot.culturespotdomain.core.auth.controller;

import com.culturespot.culturespotdomain.core.auth.service.RefreshTokenService;
import com.culturespot.culturespotdomain.core.global.security.endpoint.EndpointType;
import com.culturespot.culturespotdomain.core.global.utils.CookieUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/*
*   ✅ 구글 소셜 로그인 요청 URI : http://localhost:8080/oauth2/authorization/google
*   ✅ 소셜 로그인 통합 로그아웃 요청 URI : POST /api/public/logout
*
* */

@RestController
@AllArgsConstructor
@RequestMapping(EndpointType.PUBLIC_PATH)
public class AuthController {
    private final RefreshTokenService refreshTokenService;

    // ✅ Refresh Token을 사용하여 Access Token 재발급
    @PostMapping("/re-refresh")
    @ResponseStatus(HttpStatus.OK)
    public void generateAccessTokenFromRefreshToken(
            @CookieValue(name="refreshToken") String refreshToken,
            HttpServletResponse response
    ) {
        String issueAccessToken = refreshTokenService.generateAccessTokenFromRefreshToken(refreshToken);
        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + issueAccessToken);
    }

    // ✅ 로그아웃 API (서비스에서만 로그아웃)
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(
            @CookieValue(name="refreshToken") String refreshToken,
            HttpServletResponse response
    ) {
        refreshTokenService.deleteRefreshToken(refreshToken);
        response.addCookie(CookieUtils.deleteCookie(refreshToken));
    }


}
