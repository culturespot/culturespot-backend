package com.culturespot.culturespotdomain.core.like.repository;

import com.culturespot.culturespotdomain.core.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
