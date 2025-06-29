package com.culturespot.culturespotserviceapi.core.community.api.dto.response;

import com.culturespot.culturespotdomain.core.community.domain.entity.vo.model.read.ReadPostListDetails;

import java.util.List;

public record CommunityPostListResponse(
        int page,
        int maximumSize,
        int currentSize,
        boolean isFirstPage,
        boolean isLastPage,
        long totalElements,
        int totalPages,
        List<ReadPostListDetails> posts
) {
}
