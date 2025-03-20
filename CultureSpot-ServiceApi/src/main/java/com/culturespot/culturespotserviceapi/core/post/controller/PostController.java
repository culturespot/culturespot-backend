package com.culturespot.culturespotserviceapi.core.post.controller;

import com.culturespot.culturespotdomain.core.post.service.PostService;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotdomain.core.post.dto.PostCreateRequest;
import com.culturespot.culturespotdomain.core.post.dto.PostModifyRequest;
import com.culturespot.culturespotdomain.core.post.dto.PostSingleResponse;
import com.culturespot.culturespotserviceapi.core.auth.annotation.Auth;
import com.culturespot.culturespotserviceapi.core.auth.annotation.MemberOnly;
import com.culturespot.culturespotserviceapi.core.global.security.endpoint.EndpointType;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(EndpointType.PUBLIC_PATH + "/posts/{postId}")
    public PostSingleResponse getPostResponse(
            @PathVariable Long postId
    ) {
        return postService.getPost(postId);
    }


    @MemberOnly
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(EndpointType.USER_PATH + "/posts")
    public void createPost(
            @Auth User user,
            @ModelAttribute PostCreateRequest request
    ) {
        postService.createPost(user, request);
    }

    @MemberOnly
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(EndpointType.USER_PATH + "/posts/{postId}")
    public void modifyPost(
            @Auth User user,
            @PathVariable Long postId,
            @ModelAttribute PostModifyRequest request
    ) {
        postService.modifyPost(user, postId, request);
    }

    @MemberOnly
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(EndpointType.USER_PATH + "/posts/{postId}")
    public void deletePost(
            @Auth User user,
            @PathVariable Long postId
    ) {
        postService.deletePost(user, postId);
    }
}
