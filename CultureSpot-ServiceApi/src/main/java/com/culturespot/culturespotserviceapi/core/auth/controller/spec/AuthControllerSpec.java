package com.culturespot.culturespotserviceapi.core.auth.controller.spec;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CookieValue;

@Tag(name = "인증API", description = "사용자 인증과 관련한 API입니다.")
public interface AuthControllerSpec {

    @Operation(
            summary = "refreshToken으로 accessToken을 재발급합니다.",
            responses = @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "accessToken 재발급 성공",
                    headers = {
                            @Header(
                                    name = "Authorization",
                                    description = "재발급된 accessToken (예: Bearer eyJhbGci...)",
                                    schema = @Schema(type = "string", example = "Bearer eyJhbGciOiJIUzI1NiIsInR...")
                            )
                    }
            )

    )
    void generateAccessTokenFromRefreshToken(
            @CookieValue(name="refreshToken") String refreshToken,
            @Parameter(hidden = true) HttpServletResponse response
    );


    @Operation(
            summary = "refreshToken으로 accessToken을 재발급합니다.",
            responses = @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "로그아웃 및 refreshToken 쿠키에서 삭제"
            )
    )
    void logout(
            @CookieValue(name="refreshToken") String refreshToken,
            @Parameter(hidden = true) HttpServletResponse response
    );
}
