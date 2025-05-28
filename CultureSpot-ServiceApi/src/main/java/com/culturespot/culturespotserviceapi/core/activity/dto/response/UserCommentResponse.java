package com.culturespot.culturespotserviceapi.core.activity.dto.response;

import java.time.LocalDateTime;

public record UserCommentResponse(
        PostInfoResponse postInfo,
        Long commentId,
        String content,
        int likeCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
