package com.culturespot.culturespotdomain.core.post.repository;

import com.culturespot.culturespotdomain.core.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
