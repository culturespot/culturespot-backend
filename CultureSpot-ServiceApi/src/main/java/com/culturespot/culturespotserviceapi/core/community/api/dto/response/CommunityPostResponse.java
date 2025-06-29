package com.culturespot.culturespotserviceapi.core.community.api.dto.response;

import com.culturespot.culturespotserviceapi.core.community.api.dto.response.model.CommunityPostDetailResponseModel;

public record CommunityPostResponse (
    CommunityPostDetailResponseModel post
){
}
