package com.culturespot.culturespotserviceapi.core.community.api.dto.response.model;

public record PostAuthorResponseModel(
        Long userId,
        String username,
        int profileCode
){
}
