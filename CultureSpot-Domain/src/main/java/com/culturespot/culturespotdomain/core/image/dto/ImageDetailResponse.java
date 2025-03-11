package com.culturespot.culturespotdomain.core.image.dto;

import com.culturespot.culturespotdomain.core.image.entity.Image;
import com.culturespot.culturespotdomain.core.config.StorageBasePaths;
import lombok.Builder;

@Builder
public record ImageDetailResponse(
        String imageId,
        String imageUrl
){
    public static ImageDetailResponse from(Image image) {
        return ImageDetailResponse.builder()
                .imageId(image.getImageId().toString())
                .imageUrl(StorageBasePaths.COMMUNITY_IMAGE_PATH + image.getStoredFileName())
                .build();
    }
}
