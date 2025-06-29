package com.culturespot.culturespotserviceapi.core.activity.dto.response;

import java.time.LocalDateTime;

public record UserPostResponse(
        Long id,
        String title,
        String content,
        AuthorInfo author,
        int hits,
        int likeCount,
        int commentCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public record AuthorInfo(
            Long userId,
            String username,
            int profileCode
    ) {}
}
