package com.culturespot.culturespotdomain.core.community.infrastructure.query.projection;

import com.culturespot.culturespotdomain.core.user.entity.User;
import lombok.Getter;

@Getter
public class PostIdProjection {
    private User user;
    private Long postId;

    public PostIdProjection(User user, Long id) {
        this.user = user;
        this.postId = id;
    }
}
