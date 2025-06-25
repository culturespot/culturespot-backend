package com.culturespot.culturespotserviceapi.common.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;

import java.util.Arrays;
import java.util.Optional;

public class CookieUtils {

    /**
     * 쿠키를 생성하고 보안 설정을 적용하여 반환하는 메소드
     *
     * @param name 쿠키 이름
     * @param value 쿠키 값
     * @param maxAge 쿠키 유효기간 (초 단위)
     * @return 보안 설정이 적용된 Cookie 객체
     */
    public static Cookie createSecureCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // HTTPS 환경에서만 동작 (로컬 개발 시 false 가능)
        cookie.setMaxAge(maxAge);
        return cookie;
    }

    /**
     * ✅ ResponseCookie를 사용한 새로운 메소드 (SameSite 지원)
     */
    public static void addSecureCookieToResponse(HttpServletResponse response, String name, String value, int maxAge) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .path("/")
                .httpOnly(true)
                .secure(false) // 로컬 개발용 (프로덕션에서는 true)
                .maxAge(maxAge)
                .sameSite("Lax") // ✅ 이 부분이 핵심!
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    public static Cookie deleteCookie(String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");  // 모든 경로에서 유효하도록 설정
        cookie.setMaxAge(0);  // 즉시 삭제
        cookie.setHttpOnly(true);  // XSS 방지를 위해 HttpOnly 설정
        cookie.setSecure(true);  // HTTPS 환경에서만 전송되도록 설정 (필요 시 제거)
        return cookie;
    }

    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() == null) return Optional.empty();

        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(name))
                .findFirst();
    }
}
