package com.culturespot.culturespotserviceapi.core.auth.handler;

import com.culturespot.culturespotdomain.core.global.exception.AuthExceptionCode;
import com.culturespot.culturespotdomain.core.global.exception.ExceptionStatusMapper;
import com.culturespot.culturespotserviceapi.core.auth.dto.response.LoginFailResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException {
        AuthExceptionCode authExceptionCode = AuthExceptionCode.CANCELLED_AUTHENTICATION;
        HttpStatus status = ExceptionStatusMapper.getAuthStatus(authExceptionCode.getCode());

        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        LoginFailResponse failResponse = new LoginFailResponse(authExceptionCode.getCode(), authExceptionCode.getMessage());

        new ObjectMapper()
                .writeValue(response.getWriter(), failResponse);
    }
}

