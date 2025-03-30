package com.culturespot.culturespotserviceapi.core.image.dto.request;

import lombok.Builder;

@Builder
public record ImageDetailRequest(
    Long imageId,
    String imageUrl
){
}
