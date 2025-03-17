package com.culturespot.culturespotdomain.core.global.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

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
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);

        return cookie;
    }

    public static Cookie deleteCookie(String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");  // 모든 경로에서 유효하도록 설정
        cookie.setMaxAge(0);  // 즉시 삭제
        cookie.setHttpOnly(true);  // XSS 방지를 위해 HttpOnly 설정
        cookie.setSecure(true);  // HTTPS 환경에서만 전송되도록 설정 (필요 시 제거)
        return cookie;
    }
}
