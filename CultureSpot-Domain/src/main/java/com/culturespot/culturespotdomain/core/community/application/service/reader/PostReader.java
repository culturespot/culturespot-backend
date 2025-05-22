package com.culturespot.culturespotdomain.core.community.application.service.reader;

import com.culturespot.culturespotdomain.core.community.domain.entity.Post;
import com.culturespot.culturespotdomain.core.community.infrastructure.persistence.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostReader {

    private final PostRepository postRepository;

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Optional<Post> fetchPost(Long postId) {
        return postRepository.findById(postId);
    }
}
