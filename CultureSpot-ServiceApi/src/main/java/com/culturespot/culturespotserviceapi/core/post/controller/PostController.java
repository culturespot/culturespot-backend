package com.culturespot.culturespotserviceapi.core.post.controller;

import com.culturespot.culturespotdomain.core.post.entity.Post;
import com.culturespot.culturespotdomain.core.post.service.PostService;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.core.post.mapper.PostMapper;
import com.culturespot.culturespotdomain.core.post.dto.PostModifyCommand;
import com.culturespot.culturespotserviceapi.core.post.dto.request.PostCreateRequest;
import com.culturespot.culturespotserviceapi.core.post.dto.request.PostModifyRequest;
import com.culturespot.culturespotserviceapi.core.post.dto.response.PostSingleResponse;
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
    private final PostMapper postMapper;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(EndpointType.PUBLIC_PATH + "/posts/{postId}")
    public PostSingleResponse getPost(
            @Auth User user,
            @PathVariable Long postId
    ) {
        Post post = postService.getPost(postId);
        return postMapper.toResponse(user, post);
    }


    @MemberOnly
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(EndpointType.USER_PATH + "/posts")
    public void createPost(
            @Auth User user,
            @ModelAttribute PostCreateRequest request
    ) {
        postService.createPost(user, postMapper.toEntity(user, request));
    }

    @MemberOnly
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(EndpointType.USER_PATH + "/posts/{postId}")
    public void modifyPost(
            @Auth User user,
            @PathVariable Long postId,
            @ModelAttribute PostModifyRequest request
    ) {
        PostModifyCommand command = postMapper.toModifyCommand(request);
        postService.modifyPost(user, postId, command);
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
