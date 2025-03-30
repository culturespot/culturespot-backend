package com.culturespot.culturespotserviceapi.core.post.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostDetailsResponse(
        String nickname,
        LocalDateTime createdAt,
        Long viewCount,
        String title,
        String content
){
}
