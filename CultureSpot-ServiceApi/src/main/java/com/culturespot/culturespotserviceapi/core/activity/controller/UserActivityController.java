package com.culturespot.culturespotserviceapi.core.activity.controller;

import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.core.activity.dto.response.UserCommentResponse;
import com.culturespot.culturespotserviceapi.core.activity.dto.response.UserPostResponse;
import com.culturespot.culturespotserviceapi.core.activity.service.UserActivityService;
import com.culturespot.culturespotserviceapi.core.auth.annotation.Auth;
import com.culturespot.culturespotserviceapi.core.performance.dto.response.PerformanceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Tag(name = "마이페이지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class UserActivityController {

    private final UserActivityService userActivityService;

    @Operation(summary = "사용자가 좋아요한 이벤트 목록 조회")
    @GetMapping("/liked-events")
    public Map<String, List<PerformanceResponse>> getLikedPerformances(@Auth User user) {
        List<PerformanceResponse> responses = userActivityService.getLikedPerformances(user);
        return Map.of("likedEvents", responses);
    }

    @Operation(summary = "사용자가 작성한 댓글 목록 조회")
    @GetMapping("/comments")
    public Map<String, List<UserCommentResponse>> getUserComments(@Auth User user) {
        List<UserCommentResponse> responses = userActivityService.getUserComments(user);
        return Map.of("userComments", responses);
    }

    @Operation(summary = "사용자가 작성한 커뮤니티 게시글 목록 조회")
    @GetMapping("/posts")
    public Map<String, List<UserPostResponse>> getUserPosts(@Auth User user) {
        List<UserPostResponse> responses = userActivityService.getUserPosts(user);
        return Map.of("userPosts", responses);
    }

    @Operation(summary = "사용자가 좋아요한 커뮤니티 게시글 목록 조회")
    @GetMapping("/liked-posts")
    public Map<String, List<UserPostResponse>> getLikedPosts(@Auth User user) {
        List<UserPostResponse> responses = userActivityService.getLikedPosts(user);
        return Map.of("likedPosts", responses);
    }

}
