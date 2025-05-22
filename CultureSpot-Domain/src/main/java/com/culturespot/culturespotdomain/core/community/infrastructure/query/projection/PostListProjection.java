package com.culturespot.culturespotdomain.core.community.infrastructure.query.projection;

import com.culturespot.culturespotdomain.core.community.domain.entity.vo.model.read.ReadPostListDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PostListProjection {
    private final int page;
    private final int maximumSize;
    private final int currentSize;
    private final boolean firstPage;
    private final boolean lastPage;
    private final Long totalElements;
    private final int totalPages;
    private final List<ReadPostListDetails> readPostListDetails;

    public static PostListProjection of(Page<ReadPostListDetails> readPostListDetailsPage) {

        PostListProjection projection = new PostListProjection(
                readPostListDetailsPage.getPageable().getPageNumber(),
                readPostListDetailsPage.getPageable().getPageSize(),
                readPostListDetailsPage.getContent().size(),
                readPostListDetailsPage.isFirst(),
                readPostListDetailsPage.isLast(),
                readPostListDetailsPage.getTotalElements(),
                readPostListDetailsPage.getTotalPages(),
                readPostListDetailsPage.getContent()
        );

        return projection;
    }
}
