package com.culturespot.culturespotserviceapi.core.global.swagger;

import com.culturespot.culturespotdomain.core.global.exception.AuthExceptionCode;

public class SwaggerErrorExamples {

    public static String getAuthExceptionExample(AuthExceptionCode code) {
        return """
            {
              "code": %d,
              "message": "%s"
            }
            """.formatted(code.getCode(), code.getMessage());
    }

    public static String getDomainExceptionExample(AuthExceptionCode code) {
        return """
            {
              "code": %d,
              "message": "%s"
            }
            """.formatted(code.getCode(), code.getMessage());
    }
}

