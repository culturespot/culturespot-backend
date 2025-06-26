package com.culturespot.culturespotdomain.core.community.infrastructure.persistence;

import com.culturespot.culturespotdomain.core.community.domain.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
}
