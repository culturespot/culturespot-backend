package com.culturespot.culturespotdomain.core.image.dto;

import lombok.Builder;

@Builder
public record ImageDetailRequest(
    Long imageId,
    String imageUrl
){
}
