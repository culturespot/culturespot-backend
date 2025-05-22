package com.culturespot.culturespotdomain.core.community.domain.entity.vo.model.read;

import java.time.LocalDateTime;

public record ReadPostContent(
        Long postId,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){
}
