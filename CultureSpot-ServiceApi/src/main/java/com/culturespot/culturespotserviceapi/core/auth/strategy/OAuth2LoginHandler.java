package com.culturespot.culturespotserviceapi.core.auth.strategy;

import com.culturespot.culturespotdomain.core.global.exception.AuthException;
import com.culturespot.culturespotdomain.core.global.exception.AuthExceptionCode;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotdomain.core.user.service.UserService;
import com.culturespot.culturespotserviceapi.core.auth.dto.LoginUser;
import com.culturespot.culturespotserviceapi.core.auth.dto.response.LoginSuccessResponse;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

@Component
public class OAuth2LoginHandler {

    private final UserService userService;

    private final Map<String, Function<LoginContext, LoginSuccessResponse>> loginStrategies;

    public OAuth2LoginHandler(UserService userService) {
        this.userService = userService;
        this.loginStrategies = Map.of(
                "google", this::handleGoogle,
                "kakao", this::handleKakao
        );
    }

    public LoginSuccessResponse handle(String registrationId, String email) {
        return loginStrategies
                .getOrDefault(registrationId, this::handleDefault)
                .apply(new LoginContext(registrationId, email));
    }

    private LoginSuccessResponse handleGoogle(LoginContext ctx) {
        User user = userService.findUserOrThrow(ctx.email());
        return toResponse(user);
    }

    private LoginSuccessResponse handleKakao(LoginContext ctx) {
        User user = userService.findUserOrThrow(ctx.email());
        return toResponse(user);
    }

    private LoginSuccessResponse handleDefault(LoginContext ctx) {
        throw new AuthException(AuthExceptionCode.UNSUPPORTED_LOGIN_METHOD);
    }

    private LoginSuccessResponse toResponse(User user) {
        return new LoginSuccessResponse(
                new LoginUser(
                        user.getId(),
                        user.getNickname(),
                        user.getAuthType(),
                        user.getEmail(),
                        user.getProfileCode()
                )
        );
    }
}

