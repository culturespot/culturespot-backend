package com.culturespot.culturespotdomain.core.comment.repository;

import com.culturespot.culturespotdomain.core.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
