package com.culturespot.culturespotdomain.core.comment.repository;

import com.culturespot.culturespotdomain.core.comment.entity.Comment;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  @Query("SELECT c FROM Comment c WHERE c.post.id = :postId AND c.id > :lastId ORDER BY c.id ASC")
  Page<Comment> findByPostIdAndIdGreaterThan(
      @Param("postId") Long postId,
      @Param("lastId") Long lastId,
      Pageable pageable
  );

  Optional<Comment> findByIdAndPostId(Long commentId, Long postId);
}