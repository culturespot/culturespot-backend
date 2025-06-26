package com.culturespot.culturespotserviceapi.core.comment.controller;

import com.culturespot.culturespotdomain.core.comment.entity.Comment;
import com.culturespot.culturespotdomain.core.comment.service.CommentService;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.core.comment.controller.spec.CommentControllerSpec;
import com.culturespot.culturespotserviceapi.core.comment.dto.request.CommentRequest;
import com.culturespot.culturespotserviceapi.core.comment.dto.response.CommentResponse;
import com.culturespot.culturespotserviceapi.core.comment.dto.response.CommentResponse.CommentResponseItem;
import com.culturespot.culturespotserviceapi.core.comment.mapper.CommentMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
public class CommentController implements CommentControllerSpec {

  private final CommentService commentService;
  private final CommentMapper commentMapper;

  @Override
  public CommentResponse getComments(Long postId, Long page, Long size, Long lastId) {
    log.info("Fetching comments for postId: {}, page: {}, size: {}, lastId: {}", postId, page, size,
        lastId);
    List<Comment> comments = commentService.getComments(postId, lastId, page, size).getContent();
    List<CommentResponseItem> responseItems = commentMapper.toCommentResponses(comments);
    return CommentResponse.builder()
        .page(page)
        .size(size)
        .comments(responseItems)
        .build();
  }

  @Override
  public CommentResponseItem writeComment(Long postId, CommentRequest commentRequest, User user) {
    log.info("Writing comment for postId: {} by userId: {}", postId, user.getId());
    Comment comment = commentService.writeComment(postId, user, commentRequest.getComment());
    return commentMapper.toCommentResponse(comment);
  }

  @Override
  public CommentResponseItem editComment(Long postId, Long commentId,
      CommentRequest commentRequest, User user) {
    log.info("Editing comment with commentId: {} for postId: {} by userId: {}", commentId, postId,
        user);
    Comment updatedComment = commentService.editComment(postId, commentId, user,
        commentRequest.getComment());
    return commentMapper.toCommentResponse(updatedComment);
  }

  @Override
  public void deleteComment(Long postId, Long commentId, User user) {
    log.info("Deleting comment with commentId: {} for postId: {} by userId: {}", commentId, postId,
        user.getId());
    commentService.deleteComment(postId, commentId, user);
  }

  @Override
  public void likeComment(Long postId, Long commentId, User user) {
    log.info("Liking comment with commentId: {} for postId: {} by userId: {}", commentId, postId,
        user.getId());
    commentService.likeComment(postId, commentId, user);
  }

  @Override
  public void unlikeComment(Long postId, Long commentId, User user) {
    log.info("Unliking comment with commentId: {} for postId: {} by userId: {}", commentId, postId,
        user.getId());
    commentService.unlikeComment(postId, commentId, user);
  }
}