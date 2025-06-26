package com.culturespot.culturespotdomain.core.community.infrastructure.query;

import com.culturespot.culturespotdomain.core.community.domain.entity.vo.model.PostSortType;
import com.culturespot.culturespotdomain.core.community.domain.entity.vo.model.read.ReadPostListDetails;
import com.culturespot.culturespotdomain.core.community.infrastructure.query.projection.PostDetailProjection;
import com.culturespot.culturespotdomain.core.community.infrastructure.query.projection.PostListProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostProjectionQuerydsl {
    Optional<PostDetailProjection> fetchPostDetailWithoutLiked(Long postId);

    Page<ReadPostListDetails> searchPostList(String keyword, PostSortType sortType, Pageable pageable);
}
