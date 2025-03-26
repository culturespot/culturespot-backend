package com.culturespot.culturespotdomain.core.image.dto;

import com.culturespot.culturespotdomain.core.global.config.StorageBasePaths;
import com.culturespot.culturespotdomain.core.image.entity.Image;
import lombok.Builder;

@Builder
public record ImageDetailResponse(
        String imageId,
        String imageUrl
){
    public static ImageDetailResponse from(Image image) {
        return ImageDetailResponse.builder()
                .imageId(image.getId().toString())
                .imageUrl(StorageBasePaths.COMMUNITY_IMAGE_PATH + image.getStoredFileName())
                .build();
    }
}
