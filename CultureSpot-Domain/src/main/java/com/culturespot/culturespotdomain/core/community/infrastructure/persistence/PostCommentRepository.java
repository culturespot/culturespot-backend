package com.culturespot.culturespotdomain.core.community.infrastructure.persistence;

import com.culturespot.culturespotdomain.core.community.domain.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
}
