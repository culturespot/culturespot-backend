package com.culturespot.culturespotdomain.core.community.infrastructure.query;

import com.culturespot.culturespotdomain.core.community.domain.entity.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.culturespot.culturespotdomain.core.community.domain.entity.QPost.post;
import static com.culturespot.culturespotdomain.core.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class PostQuerydslImpl implements PostQuerydsl {

    private final JPAQueryFactory queryFactory;

    public Optional<Post> findByIdWithUser(Long postId) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(post)
                        .join(post.user, user).fetchJoin()
                        .where(post.id.eq(postId))
                        .fetchOne()
        );
    }
}
