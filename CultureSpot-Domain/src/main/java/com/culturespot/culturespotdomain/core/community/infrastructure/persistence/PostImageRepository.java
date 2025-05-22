package com.culturespot.culturespotdomain.core.community.infrastructure.persistence;

import com.culturespot.culturespotdomain.core.community.domain.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
