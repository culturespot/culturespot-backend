package com.culturespot.culturespotdomain.core.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthExceptionCode {
    TOKEN(1000),
    UNKNOWN_ERROR(TOKEN.code + 1, "알 수 없는 오류가 발생했습니다. 관리자에게 문의하세요."),
    // Access token
    EXPIRED_ACCESS_TOKEN(TOKEN.code + 2, "JWT가 만료되었습니다."),
    MALFORMED_ACCESS_TOKEN(TOKEN.code + 3, "JWT 형식이 잘못되었습니다."),
    SIGNATURE_ACCESS_TOKEN(TOKEN.code + 4, "JWT 서명이 유효하지 않습니다."),
    // Refresh token
    EXPIRED_REFRESH_TOKEN(TOKEN.code + 2, "Refresh token이 만료되었습니다. 다시 로그인해주세요"),
    MALFORMED_REFRESH_TOKEN(TOKEN.code + 3, "Refresh token 형식이 잘못되었습니다."),
    SIGNATURE_REFRESH_TOKEN(TOKEN.code + 4, "Refresh token 서명이 유효하지 않습니다."),

    ROLE(900),

    USER(800),
    USER_NOT_FOUND(USER.code + 1, "사용자를 찾을 수 없습니다."),
    UNAUTHENTICATED_USER(USER.code + 2, "인증된 사용자만 접근 가능합니다."),
    INVALID_AUTHENTICATION(USER.code + 3, "잘못된 인증 정보입니다.");

    private final int code;
    private final String message;

    AuthExceptionCode(int code) {
        this.code = code;
        this.message = "";
    }
}
