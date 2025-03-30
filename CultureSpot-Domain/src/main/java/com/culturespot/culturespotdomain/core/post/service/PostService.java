package com.culturespot.culturespotdomain.core.post.service;

import com.culturespot.culturespotdomain.core.post.entity.Post;
import com.culturespot.culturespotdomain.core.post.dto.PostModifyCommand;
import com.culturespot.culturespotdomain.core.user.entity.User;
import org.springframework.transaction.annotation.Transactional;

public interface PostService {
    Post getPost(Long postId);

    @Transactional
    void createPost(User user, Post post);

    void modifyPost(User user, Long postId, PostModifyCommand command);
    void deletePost(User user, Long postId);
}
