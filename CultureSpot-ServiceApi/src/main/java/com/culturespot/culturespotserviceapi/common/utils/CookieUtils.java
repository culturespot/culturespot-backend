package com.culturespot.culturespotserviceapi.common.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;

import java.util.Arrays;
import java.util.Optional;

public class CookieUtils {

    /**
     * 기존 쿠키 생성 메소드 (기존 호환성 유지)
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
     * ✅ SameSite 지원이 포함된 새로운 쿠키 생성 메소드
     */
    public static void addSecureCookieToResponse(HttpServletResponse response, String name, String value, int maxAge) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .path("/")
                .httpOnly(true)
                .secure(false) // 로컬 개발용 (프로덕션에서는 true로 변경)
                .maxAge(maxAge)
                .sameSite("Lax") // ✅ SameSite 속성 추가
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
        System.out.println("쿠키 생성됨: " + cookie.toString()); // 디버깅용
    }

    /**
     * 쿠키 삭제
     */
    public static Cookie deleteCookie(String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");  // 모든 경로에서 유효하도록 설정
        cookie.setMaxAge(0);  // 즉시 삭제
        cookie.setHttpOnly(true);  // XSS 방지를 위해 HttpOnly 설정
        cookie.setSecure(false);  // 로컬 개발용 (프로덕션에서는 true)
        return cookie;
    }

    /**
     * 요청에서 특정 이름의 쿠키 가져오기
     */
    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() == null) return Optional.empty();

        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(name))
                .findFirst();
    }
}