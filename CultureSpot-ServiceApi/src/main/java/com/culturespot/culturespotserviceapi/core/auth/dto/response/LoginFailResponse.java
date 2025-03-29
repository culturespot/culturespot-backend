package com.culturespot.culturespotserviceapi.core.auth.dto.response;

public record LoginFailResponse (
        int code,
        String message
){
}
