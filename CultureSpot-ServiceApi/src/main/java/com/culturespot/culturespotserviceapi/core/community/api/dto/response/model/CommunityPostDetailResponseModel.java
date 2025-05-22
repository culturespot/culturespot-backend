package com.culturespot.culturespotserviceapi.core.community.api.dto.response.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public record CommunityPostDetailResponseModel(
        @JsonUnwrapped
        PostContentResponseModel post,

        @JsonUnwrapped
        LikeCountResponseModel like,

        Long commentCount,

        List<StoredImageUrlResponseModel> image,

        PostAuthorResponseModel author
){
}
