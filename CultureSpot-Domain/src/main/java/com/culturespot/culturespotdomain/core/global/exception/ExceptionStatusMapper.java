package com.culturespot.culturespotdomain.core.global.exception;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class ExceptionStatusMapper {

    private static final Map<Integer, HttpStatus> AUTH_STATUS_MAP = new HashMap<>();
    private static final Map<Integer, HttpStatus> DOMAIN_STATUS_MAP = new HashMap<>();

    static {
        /* ✅ AuthException 상태 코드 매핑 */
        // TOKEN
        AUTH_STATUS_MAP.put(AuthExceptionCode.UNKNOWN_ERROR.getCode(), HttpStatus.INTERNAL_SERVER_ERROR); //🔥500
        // access-token
        AUTH_STATUS_MAP.put(AuthExceptionCode.EXPIRED_ACCESS_TOKEN.getCode(), HttpStatus.UNAUTHORIZED); //🔥401
        AUTH_STATUS_MAP.put(AuthExceptionCode.MALFORMED_ACCESS_TOKEN.getCode(), HttpStatus.BAD_REQUEST); //🔥400
        AUTH_STATUS_MAP.put(AuthExceptionCode.SIGNATURE_ACCESS_TOKEN.getCode(), HttpStatus.UNAUTHORIZED); //🔥401
        // refresh-token

        // USER
        AUTH_STATUS_MAP.put(AuthExceptionCode.USER_NOT_FOUND.getCode(), HttpStatus.NOT_FOUND); //🔥404
        AUTH_STATUS_MAP.put(AuthExceptionCode.UNAUTHENTICATED_USER.getCode(), HttpStatus.FORBIDDEN); //🔥403
        AUTH_STATUS_MAP.put(AuthExceptionCode.INVALID_AUTHENTICATION.getCode(), HttpStatus.UNAUTHORIZED); //🔥401

        /* ✅ DomainException 상태 코드 매핑 */
        DOMAIN_STATUS_MAP.put(DomainExceptionCode.POST_NOT_FOUND.getCode(), HttpStatus.NOT_FOUND); //🔥404
        DOMAIN_STATUS_MAP.put(DomainExceptionCode.POST_DELETE_PERMISSION_DENIED.getCode(), HttpStatus.FORBIDDEN); //🔥403
        DOMAIN_STATUS_MAP.put(DomainExceptionCode.POST_EDIT_PERMISSION_DENIED.getCode(), HttpStatus.FORBIDDEN); //🔥403
        DOMAIN_STATUS_MAP.put(DomainExceptionCode.IMAGE_EXTENSION_NOT_FOUND.getCode(), HttpStatus.BAD_REQUEST); //🔥400
        DOMAIN_STATUS_MAP.put(DomainExceptionCode.UNSUPPORTED_IMAGE_EXTENSIONS.getCode(), HttpStatus.BAD_REQUEST); //🔥400
        DOMAIN_STATUS_MAP.put(DomainExceptionCode.FILE_NOT_FOUND.getCode(), HttpStatus.NOT_FOUND); //🔥404
        DOMAIN_STATUS_MAP.put(DomainExceptionCode.FILE_UPLOAD_FAIL.getCode(), HttpStatus.INTERNAL_SERVER_ERROR); //🔥500
        DOMAIN_STATUS_MAP.put(DomainExceptionCode.FILE_DELETE_FAIL.getCode(), HttpStatus.INTERNAL_SERVER_ERROR); //🔥500
    }

    public static HttpStatus getAuthStatus(int code) {
        return AUTH_STATUS_MAP.getOrDefault(code, HttpStatus.BAD_REQUEST); //🔥400
    }

    public static HttpStatus getDomainStatus(int code) {
        return DOMAIN_STATUS_MAP.getOrDefault(code, HttpStatus.BAD_REQUEST); //🔥400
    }

    public static HttpStatus getDefaultRuntimeStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR; // 🔥 예상하지 못한 RuntimeException 처리
    }
}