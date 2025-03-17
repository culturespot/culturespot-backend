package com.culturespot.culturespotdomain.core.post.dto;

import com.culturespot.culturespotdomain.core.image.dto.ImageDetailResponse;
import com.culturespot.culturespotdomain.core.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
public record PostSingleResponse(
        @JsonUnwrapped
        PostDetailsResponse postDetails,
        List<ImageDetailResponse> images
){
    public static PostSingleResponse from(Post post) {
        List<ImageDetailResponse> imageDetailResponseDetails = Optional.ofNullable(post.getImages())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(ImageDetailResponse::from)
                .collect(Collectors.toList());

        return PostSingleResponse.builder()
                .postDetails(PostDetailsResponse.builder()
                        .nickname(post.getUser().getEmail())
                        .createdAt(post.getCreatedAt())
                        .viewCount(post.getViewCount())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .build())
                .images(imageDetailResponseDetails)
                .build();
    }

}
