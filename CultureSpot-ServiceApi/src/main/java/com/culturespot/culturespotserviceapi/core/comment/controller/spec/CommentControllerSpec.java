package com.culturespot.culturespotserviceapi.core.comment.controller.spec;

import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.core.auth.annotation.Auth;
import com.culturespot.culturespotserviceapi.core.comment.dto.request.CommentRequest;
import com.culturespot.culturespotserviceapi.core.comment.dto.response.CommentResponse;
import com.culturespot.culturespotserviceapi.core.comment.dto.response.CommentResponse.CommentResponseItem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "PRIVATE-댓글 API")
public interface CommentControllerSpec {

  @Operation(
      summary = "댓글 목록 조회",
      description = "댓글 목록 조회와 관련한 API입니다.",
      responses = {
          @io.swagger.v3.oas.annotations.responses.ApiResponse(
              responseCode = "200",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(
                      implementation = CommentResponse.class
                  )
              )
          )
      }
  )
  CommentResponse getComments(
      @PathVariable Long postId,
      @PositiveOrZero
      @RequestParam(required = false, defaultValue = "1") Long page,
      @Min(1)
      @Max(50)
      @RequestParam(required = false, defaultValue = "10") Long size,
      @PositiveOrZero
      @RequestParam(required = false, defaultValue = "0") Long lastId
  );

  @Operation(
      summary = "댓글 작성",
      responses = {
          @io.swagger.v3.oas.annotations.responses.ApiResponse(
              responseCode = "200",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(
                      implementation = CommentResponseItem.class
                  )
              )
          )
      }
  )
  CommentResponseItem writeComment(@PathVariable Long postId,
      @RequestBody CommentRequest commentRequest,
      @io.swagger.v3.oas.annotations.Parameter(hidden = true)
      @Auth User user);

  @Operation(
      summary = "댓글 수정",
      responses = {
          @io.swagger.v3.oas.annotations.responses.ApiResponse(
              responseCode = "200",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(
                      implementation = CommentResponseItem.class
                  )
              )
          )
      }
  )
  CommentResponseItem editComment(@PathVariable Long postId,
      @PathVariable Long commentId,
      @RequestBody CommentRequest commentRequest,
      @io.swagger.v3.oas.annotations.Parameter(hidden = true)
      @Auth User user);

  @Operation(
      summary = "댓글 삭제",
      responses = {
          @io.swagger.v3.oas.annotations.responses.ApiResponse(
              responseCode = "200"
          )
      }
  )
  void deleteComment(
      @PathVariable Long postId,
      @PathVariable Long commentId,
      @io.swagger.v3.oas.annotations.Parameter(hidden = true)
      @Auth User user);

  @Operation(
      summary = "댓글 좋아요",
      responses = {
          @io.swagger.v3.oas.annotations.responses.ApiResponse(
              responseCode = "200"
          )
      }
  )
  void likeComment(
      @PathVariable Long postId,
      @PathVariable Long commentId,
      @io.swagger.v3.oas.annotations.Parameter(hidden = true)
      @Auth User user);

  @Operation(
      summary = "댓글 좋아요 삭제",
      responses = {
          @io.swagger.v3.oas.annotations.responses.ApiResponse(
              responseCode = "200"
          )
      }
  )
  void unlikeComment(
      @PathVariable Long postId,
      @PathVariable Long commentId,
      @io.swagger.v3.oas.annotations.Parameter(hidden = true)
      @Auth User user);
}