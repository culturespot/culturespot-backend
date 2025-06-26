package com.culturespot.culturespotdomain.core.community.infrastructure.persistence;

import com.culturespot.culturespotdomain.core.community.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findById(Long postId);
}