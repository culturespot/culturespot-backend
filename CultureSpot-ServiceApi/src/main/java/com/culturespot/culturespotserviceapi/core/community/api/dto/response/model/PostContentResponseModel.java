package com.culturespot.culturespotserviceapi.core.community.api.dto.response.model;

import java.time.LocalDateTime;

public record PostContentResponseModel(
        Long postId,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
