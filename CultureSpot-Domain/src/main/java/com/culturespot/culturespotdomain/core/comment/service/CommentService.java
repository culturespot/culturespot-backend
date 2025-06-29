package com.culturespot.culturespotdomain.core.comment.service;

import com.culturespot.culturespotdomain.core.comment.entity.Comment;
import com.culturespot.culturespotdomain.core.comment.repository.CommentRepository;
//import com.culturespot.culturespotdomain.core.post.entity.Post;
//import com.culturespot.culturespotdomain.core.post.repository.PostRepository;
import com.culturespot.culturespotdomain.core.community.domain.entity.Post;
import com.culturespot.culturespotdomain.core.community.infrastructure.persistence.PostRepository;
import com.culturespot.culturespotdomain.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;

  @Transactional(readOnly = true)
  public Page<Comment> getComments(Long postId, Long lastId, Long page, Long size) {
    PageRequest pageRequest = PageRequest.of(page.intValue(), size.intValue());
    return commentRepository.findByPostIdAndIdGreaterThan(postId, lastId, pageRequest);
  }

  @Transactional
  public Comment writeComment(Long postId, User user, String content) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new IllegalArgumentException("Post not found"));

    Comment comment = Comment.builder()
        .post(post)
        .user(user)
        .content(content)
        .build();
    return commentRepository.save(comment);
  }

  @Transactional
  public Comment editComment(Long postId, Long commentId, User user, String content) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new IllegalArgumentException("Post not found"));

    Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
        .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

    if (!comment.getUser().equals(user)) {
      throw new IllegalArgumentException("User not authorized to edit this comment");
    }

    comment.updateComment(content);
    return commentRepository.save(comment);
  }

  @Transactional
  public void deleteComment(Long postId, Long commentId, User user) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new IllegalArgumentException("Post not found"));

    Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
        .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

    if (!comment.getUser().equals(user)) {
      throw new IllegalArgumentException("User not authorized to delete this comment");
    }

    commentRepository.delete(comment);
  }

  @Transactional
  public void likeComment(Long postId, Long commentId, User user) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new IllegalArgumentException("Post not found"));

    Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
        .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

    comment.incrementLikes(user.getId());
    commentRepository.save(comment);
  }

  @Transactional
  public void unlikeComment(Long postId, Long commentId, User user) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new IllegalArgumentException("Post not found"));

    Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
        .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

    comment.decrementLikes(user.getId());
    commentRepository.save(comment);
  }
}