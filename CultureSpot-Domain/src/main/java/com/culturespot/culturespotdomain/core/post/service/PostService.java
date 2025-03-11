package com.culturespot.culturespotdomain.core.post.service;

import com.culturespot.culturespotdomain.core.post.dto.PostCreateRequest;
import com.culturespot.culturespotdomain.core.post.dto.PostModifyRequest;
import com.culturespot.culturespotdomain.core.post.dto.PostSingleResponse;
import com.culturespot.culturespotdomain.core.user.entity.User;

public interface PostService {
    PostSingleResponse getPost(Long postId);
    void createPost(User user, PostCreateRequest request);
    void modifyPost(User user, Long postId, PostModifyRequest request);
    void deletePost(User user, Long postId);
}
