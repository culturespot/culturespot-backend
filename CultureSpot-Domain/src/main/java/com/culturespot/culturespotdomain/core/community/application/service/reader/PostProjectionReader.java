package com.culturespot.culturespotdomain.core.community.application.service.reader;

import com.culturespot.culturespotdomain.core.community.domain.entity.Post;
import com.culturespot.culturespotdomain.core.community.infrastructure.persistence.PostRepository;
import com.culturespot.culturespotdomain.core.community.infrastructure.query.PostProjectionQuerydsl;
import com.culturespot.culturespotdomain.core.community.infrastructure.query.projection.PostDetailProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostProjectionReader {

    private final PostProjectionQuerydsl postProjectionQuerydsl;

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Optional<PostDetailProjection> fetchPostDetailWithoutLiked(Long postId) {
        return postProjectionQuerydsl.fetchPostDetailWithoutLiked(postId);
    }

}
