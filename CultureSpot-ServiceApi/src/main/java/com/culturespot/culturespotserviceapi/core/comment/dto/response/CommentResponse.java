package com.culturespot.culturespotserviceapi.core.comment.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentResponse {

  private Long page;
  private Long size;
  private List<CommentResponseItem> comments;

  @Getter
  @Builder
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class CommentResponseItem {

    private Long id;
    private Long postId;
    private Long userId;
    private String content;
    private Integer likedCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
  }
}