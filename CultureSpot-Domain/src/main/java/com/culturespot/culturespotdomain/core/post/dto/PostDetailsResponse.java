package com.culturespot.culturespotdomain.core.post.dto;

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
