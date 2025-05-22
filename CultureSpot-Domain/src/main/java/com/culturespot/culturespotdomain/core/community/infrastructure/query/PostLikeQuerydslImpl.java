package com.culturespot.culturespotdomain.core.community.infrastructure.query;

import com.culturespot.culturespotdomain.core.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.culturespot.culturespotdomain.core.community.domain.entity.QPostLike.postLike;

@Repository
@RequiredArgsConstructor
public class PostLikeQuerydslImpl implements PostLikeQuerydsl {

    private final JPAQueryFactory queryFactory;

    public boolean hasUserLikedPost(User user, Long postId) {
        if (postId == null || user.getId() == null) return false;

        Integer result = queryFactory
                .selectOne()
                .from(postLike)
                .where(
                        postLike.post.id.eq(postId),
                        postLike.user.id.eq(user.getId())
                )
                .fetchFirst();

        return result != null;
    }
}
