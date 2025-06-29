package com.culturespot.culturespotserviceapi.core.community.api.controller;

import com.culturespot.culturespotdomain.core.community.domain.entity.Post;
import com.culturespot.culturespotdomain.core.community.domain.entity.vo.model.PostSortType;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.core.auth.annotation.Auth;
import com.culturespot.culturespotserviceapi.core.community.api.dto.request.WriteCommunityPostRequest;
import com.culturespot.culturespotserviceapi.core.community.api.dto.response.CommunityPostListResponse;
import com.culturespot.culturespotserviceapi.core.community.api.facade.CommunityFacade;
import com.culturespot.culturespotserviceapi.core.community.api.dto.response.CommunityPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommunityPostController {
    private final CommunityFacade communityFacade;

    @GetMapping("/public/posts")
    @ResponseStatus(HttpStatus.OK)
    public CommunityPostListResponse fetchPostList(
            @Auth User user,
            @RequestParam(defaultValue = "latest") PostSortType sortType,
            @RequestParam(required = false) String keyword,
            Pageable pageable // page, size, sort
    ) {
        return communityFacade.loadByCommunityPosts(user, keyword, sortType, pageable);
    }

    @GetMapping("/public/posts/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public CommunityPostResponse fetchPost(
            @Auth User user,
            @PathVariable("postId") Long postId) {
        return communityFacade.loadByCommunityPostDetail(user, postId);
    }

    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Map<String, Long> createPost(
            @Auth User user,
            @ModelAttribute WriteCommunityPostRequest request
    ) {
        return communityFacade.registerCommunityPost(user, request);
    }

    @DeleteMapping("/posts/{postId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public void deletePost(
            @Auth User user,
            @PathVariable("postId") Long postId
    ) {
        communityFacade.deleteCommunityPost(user, postId);
    }


}
