package com.culturespot.culturespotdomain.core.community.infrastructure.query;

import com.culturespot.culturespotdomain.core.community.domain.entity.Post;

import java.util.Optional;

public interface PostQuerydsl {
    Optional<Post> findByIdWithUser(Long postId);
}
