package com.culturespot.culturespotdomain.core.community.domain.entity.vo.model.read;

public record ReadPostListDetails(
        Long postId,
        String title,
        String content,
        Integer hits,
        Integer likeCount,
        Integer commentCount,
        String createdAt,
        String updatedAt,

        ReadPostAuthor author
) {
}