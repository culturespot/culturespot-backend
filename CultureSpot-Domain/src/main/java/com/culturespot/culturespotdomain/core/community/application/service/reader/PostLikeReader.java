package com.culturespot.culturespotdomain.core.community.application.service.reader;

import com.culturespot.culturespotdomain.core.community.infrastructure.query.PostLikeQuerydsl;
import com.culturespot.culturespotdomain.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeReader {
    private final PostLikeQuerydsl postLikeQuerydsl;

    public boolean hasUserLikedPost(User user, Long postId) {
        return postLikeQuerydsl.hasUserLikedPost(user, postId);
    }
}
