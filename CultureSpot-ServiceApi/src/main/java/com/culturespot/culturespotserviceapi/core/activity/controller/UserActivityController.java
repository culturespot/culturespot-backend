package com.culturespot.culturespotserviceapi.core.activity.controller;

import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.core.activity.dto.response.UserCommentResponse;
import com.culturespot.culturespotserviceapi.core.activity.dto.response.UserPostResponse;
import com.culturespot.culturespotserviceapi.core.activity.service.UserActivityService;
import com.culturespot.culturespotserviceapi.core.auth.annotation.Auth;
import com.culturespot.culturespotserviceapi.core.performance.dto.response.PerformanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class UserActivityController {

    private final UserActivityService userActivityService;

    @GetMapping("/liked-events")
    public Map<String, List<PerformanceResponse>> getLikedPerformances(@Auth User user) {
        List<PerformanceResponse> responses = userActivityService.getLikedPerformances(user);
        return Map.of("likedEvents", responses);
    }

    @GetMapping("/comments")
    public Map<String, List<UserCommentResponse>> getUserComments(@Auth User user) {
        List<UserCommentResponse> responses = userActivityService.getUserComments(user);
        return Map.of("userComments", responses);
    }

    @GetMapping("/posts")
    public Map<String, List<UserPostResponse>> getUserPosts(@Auth User user) {
        List<UserPostResponse> responses = userActivityService.getUserPosts(user);
        return Map.of("userPosts", responses);
    }

    @GetMapping("/liked-posts")
    public Map<String, List<UserPostResponse>> getLikedPosts(@Auth User user) {
        List<UserPostResponse> responses = userActivityService.getLikedPosts(user);
        return Map.of("likedPosts", responses);
    }

}
