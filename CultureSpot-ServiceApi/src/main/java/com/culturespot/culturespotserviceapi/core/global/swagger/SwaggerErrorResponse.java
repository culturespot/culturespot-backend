package com.culturespot.culturespotserviceapi.core.global.swagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Schema(description = "에러 응답")
public class SwaggerErrorResponse {
        @Schema(description = "에러 코드")
        private final int code;

        @Schema(description = "에러 메시지")
        private final String message;
}
