package com.culturespot.culturespotdomain.core.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /* ✅ AuthException (인증 관련 예외 처리) */
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Map<String, Object>> handleAuthException(AuthException e) {
        HttpStatus status = ExceptionStatusMapper.getAuthStatus(e.getCode());

        return ResponseEntity
                .status(status)
                .body(Map.of(
                        "code", e.getCode(),
                        "message", e.getMessage()
                ));
    }

    /* ✅ DomainException (게시글, 파일, 이미지 관련 예외 처리) */
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Map<String, Object>> handleDomainException(DomainException e) {
        HttpStatus status = ExceptionStatusMapper.getDomainStatus(e.getCode());

        return ResponseEntity
                .status(status)
                .body(Map.of(
                        "code", e.getCode(),
                        "message", e.getMessage()
                ));
    }

    /* ✅ Cookie가 없을 때 발생하는 예외 처리 */
    /* 차후, 범용적으로 사용할 수 있게 리팩토링 고민해보기 */
    @ExceptionHandler(MissingRequestCookieException.class)
    public ResponseEntity<Map<String, Object>> handleMissingCookieException(MissingRequestCookieException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "code", "MISSING_COOKIE_EXCEPTION",
                        "message", "포함해야하는 쿠키 값이 누락되었습니다. 확인해주세요."
                ));
    }

    /* ✅ 검증 실패시의 예외처리 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationError(MethodArgumentNotValidException e) {
        FieldError firstError = e.getBindingResult().getFieldErrors().get(0);

        int statusCode = HttpStatus.BAD_REQUEST.value();
        String message = firstError.getDefaultMessage();

        return ResponseEntity
                .status(statusCode)
                .body(Map.of(
                        "code", statusCode,
                        "message", message
                ));
    }

    /* ✅ 예상하지 못한 RuntimeException 처리 */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleUnexpectedException(RuntimeException e) {
        HttpStatus status = ExceptionStatusMapper.getDefaultRuntimeStatus();

        return ResponseEntity
                .status(status)
                .body(Map.of(
                        "code", 500,
                        "message", "서버 내부 오류가 발생했습니다. 관리자에게 문의하세요."
                ));
    }
}
