package com.culturespot.culturespotserviceapi.core.comment.mapper;

import com.culturespot.culturespotdomain.core.comment.entity.Comment;
import com.culturespot.culturespotserviceapi.core.comment.dto.response.CommentResponse.CommentResponseItem;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

  @Mapping(target = "userId", source = "user.id")
  @Mapping(target = "postId", source = "post.id")
  @Mapping(target = "likedCount", expression = "java(comment.getLikedUserIds().size())")
  CommentResponseItem toCommentResponse(Comment comment);

  List<CommentResponseItem> toCommentResponses(List<Comment> comments);
}