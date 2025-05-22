package com.culturespot.culturespotserviceapi.core.community.api.dto.response.model;

public record LikeCountResponseModel(
        boolean liked,
        Long likeCount
){
}
