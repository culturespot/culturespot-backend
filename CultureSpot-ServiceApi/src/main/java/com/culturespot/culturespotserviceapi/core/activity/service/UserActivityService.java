package com.culturespot.culturespotserviceapi.core.activity.service;

import com.culturespot.culturespotdomain.core.comment.entity.Comment;
import com.culturespot.culturespotdomain.core.comment.repository.CommentRepository;
import com.culturespot.culturespotdomain.core.community.domain.entity.Post;
import com.culturespot.culturespotdomain.core.community.infrastructure.persistence.PostRepository;
import com.culturespot.culturespotdomain.core.performance.entity.Performance;
import com.culturespot.culturespotdomain.core.performance.repository.PerformanceLikeRepository;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.core.activity.dto.response.PostInfoResponse;
import com.culturespot.culturespotserviceapi.core.activity.dto.response.UserCommentResponse;
import com.culturespot.culturespotserviceapi.core.activity.dto.response.UserPostResponse;
import com.culturespot.culturespotserviceapi.core.performance.dto.response.PerformanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserActivityService {

    private final PerformanceLikeRepository likeRepository;
    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    public List<PerformanceResponse> getLikedPerformances(User user) {

        List<Performance> performances = likeRepository.findPerformancesLikedByUserId(user.getId());

        return performances.stream()
                .map(p -> new PerformanceResponse(
                        p.getId(),
                        p.getTitle(),
                        p.getType(),
                        p.getCategory(),
                        p.getPlace(),
                        p.getStartDate(),
                        p.getEndDate(),
                        p.getPerformanceInfo() != null ? p.getPerformanceInfo().getImageUrl() : null,
                        true
                ))
                .toList();
    }


    public List<UserCommentResponse> getUserComments(User user) {
        List<Comment> comments = commentRepository.findByUserIdOrderByCreatedAtDesc(user.getId());

        return comments.stream()
                .map(c -> new UserCommentResponse(
                        new PostInfoResponse(
                                c.getPost().getId(),
                                c.getPost().getTitle()
                        ),
                        c.getId(),
                        c.getContent(),
                        c.getLikedUserIds() != null ? c.getLikedUserIds().size() : 0,
                        c.getCreatedAt(),
                        c.getModifiedAt()
                ))
                .toList();
    }

    public List<UserPostResponse> getUserPosts(User user) {
        List<Post> posts = postRepository.findByUserIdOrderByCreatedAtDesc(user.getId());

        return posts.stream()
                .map(p -> new UserPostResponse(
                        p.getId(),
                        p.getTitle(),
                        p.getContent(),
                        new UserPostResponse.AuthorInfo(
                            p.getUser().getId(),
                            p.getUser().getNickname(),
                            p.getUser().getProfileCode()
                        ),
                        p.getViewCount().intValue(),
                        p.getPostLikes() != null ? p.getPostLikes().size() : 0,
                        p.getPostComments() != null ? p.getPostComments().size() : 0,
                        p.getCreatedAt(),
                        p.getUpdatedAt()
                ))
                .toList();
    }

}
