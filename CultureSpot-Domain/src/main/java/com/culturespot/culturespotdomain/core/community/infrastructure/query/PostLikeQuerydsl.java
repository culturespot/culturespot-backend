package com.culturespot.culturespotdomain.core.community.infrastructure.query;

import com.culturespot.culturespotdomain.core.user.entity.User;

public interface PostLikeQuerydsl {
    boolean hasUserLikedPost(User user, Long userId);
}
