package com.culturespot.culturespotdomain.core.community.infrastructure.query.projection;

import com.culturespot.culturespotdomain.core.community.domain.entity.vo.model.read.ReadStoredImageUrl;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostDetailProjection {

    // PostContent
    private Long postId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // LikeCount
    private Boolean liked;
    private Long likeCount;

    // 댓글 수
    private Long commentCount;

    // PostAuthor
    private Long userId;
    private String username;
    private int profileCode;

    // StoredImageUrl
    private List<ReadStoredImageUrl> readStoredImageUrls;

    @Builder
    public PostDetailProjection(
            Long postId,
            String title,
            String content,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Boolean liked,
            Long likeCount,
            Long commentCount,
            Long userId,
            String username,
            int profileCode,
            List<ReadStoredImageUrl> readStoredImageUrls
    ) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.liked = liked;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.userId = userId;
        this.username = username;
        this.profileCode = profileCode;
        this.readStoredImageUrls = readStoredImageUrls;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public void setReadStoredImageUrls(ReadStoredImageUrl image) {
        if (this.readStoredImageUrls == null) {
            this.readStoredImageUrls = new ArrayList<>();
        }
        this.readStoredImageUrls.add(image);
    }
}





































//public record FlatCommunityPostDetailProjection(
//        PostContent postContent,
//
//        LikeCount likeCount,
//
//        Long commentCount,
//
//        List<StoredImageUrl> storedImageUrls,
//
//        PostAuthor postAuthor
//){
//}