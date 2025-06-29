package com.culturespot.culturespotdomain.core.community.infrastructure.query;

import com.culturespot.culturespotdomain.core.community.domain.entity.QPost;
import com.culturespot.culturespotdomain.core.community.domain.entity.QPostComment;
import com.culturespot.culturespotdomain.core.community.domain.entity.QPostLike;
import com.culturespot.culturespotdomain.core.community.domain.entity.vo.model.PostSortType;
import com.culturespot.culturespotdomain.core.community.domain.entity.vo.model.read.ReadPostAuthor;
import com.culturespot.culturespotdomain.core.community.domain.entity.vo.model.read.ReadPostListDetails;
import com.culturespot.culturespotdomain.core.community.domain.entity.vo.model.read.ReadStoredImageUrl;
import com.culturespot.culturespotdomain.core.community.infrastructure.query.projection.PostDetailProjection;
import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.culturespot.culturespotdomain.core.community.domain.entity.QPost.post;
import static com.culturespot.culturespotdomain.core.community.domain.entity.QPostComment.postComment;
import static com.culturespot.culturespotdomain.core.community.domain.entity.QPostImage.postImage;
import static com.culturespot.culturespotdomain.core.community.domain.entity.QPostLike.postLike;
import static com.culturespot.culturespotdomain.core.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class PostProjectionQuerydslImpl implements PostProjectionQuerydsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<PostDetailProjection> fetchPostDetailWithoutLiked(Long postId) {
        
        List<Tuple> rows = queryFactory
                .select(
                        user.id,
                        user.nickname,
                        user.profileCode,

                        post.id,
                        post.title,
                        post.content,
                        post.createdAt,
                        post.updatedAt,

                        postImage.id,
                        postImage.storedFileName,

                        postLike.id.countDistinct(),
                        postComment.id.countDistinct()
                )
                .from(post)
                .join(post.user, user)
                .leftJoin(postLike).on(postLike.post.eq(post))
                .leftJoin(postComment).on(postComment.post.eq(post))
                .leftJoin(postImage).on(postImage.post.eq(post))
                .where(post.id.eq(postId))
                .groupBy(
                        user.id,
                        post.id,
                        postImage.id
                )
                .fetch();

        Tuple firstData = rows.get(0);
        PostDetailProjection projection = PostDetailProjection.builder()
                .userId(firstData.get(user.id))
                .username(firstData.get(user.nickname))
                .profileCode(firstData.get(user.profileCode))

                .postId(firstData.get(post.id))
                .title(firstData.get(post.title))
                .content(firstData.get(post.content))
                .createdAt(firstData.get(post.createdAt))
                .updatedAt(firstData.get(post.updatedAt))

                .likeCount(firstData.get(postLike.post.id.countDistinct()))
                .commentCount(firstData.get(postComment.post.id.countDistinct()))

                .build();

        for (Tuple row : rows) {
            Long imageId = row.get(postImage.id);
            String url = row.get(postImage.storedFileName);
            if (imageId != null && url != null) {
                projection.setReadStoredImageUrls(new ReadStoredImageUrl(imageId, url));
            }
        }

        return Optional.of(projection);
    }

    @Override
    public Page<ReadPostListDetails> searchPostList(String keyword, PostSortType sortType, Pageable pageable) {

        OrderSpecifier<?> order = getPostListOrderSpecifier(sortType, post, postLike, postComment);

        List<ReadPostListDetails> content = queryFactory
                .selectFrom(post)
                .join(post.user, user)
                .leftJoin(postLike).on(postLike.post.eq(post))
                .leftJoin(postComment).on(postComment.post.eq(post))
                .where(
                        keyword != null && !keyword.isBlank() ? post.title.contains(keyword) : null
                )
                .groupBy(post.id)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(order)
                .transform(GroupBy.groupBy(post.id).list(
                        Projections.constructor(ReadPostListDetails.class,
                                post.id,
                                post.title,
                                post.content,
                                post.viewCount,
                                postLike.id.countDistinct(),
                                postComment.id.countDistinct(),
                                post.createdAt,
                                post.updatedAt,

                                Projections.constructor(ReadPostAuthor.class,
                                        user.id,
                                        user.nickname,
                                        user.profileCode
                                )
                        )
                ));

        JPAQuery<Long> countQuery = queryFactory
                .select(post.countDistinct())
                .from(post)
                .where(keyword != null && !keyword.isBlank() ? post.title.contains(keyword) : null);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private OrderSpecifier<?> getPostListOrderSpecifier(PostSortType sortType, QPost post, QPostLike like, QPostComment comment) {
        return switch (sortType) {
            case latest -> post.createdAt.desc();
            case oldest -> post.createdAt.asc();
            case mostLike -> like.id.countDistinct().desc();
            case mostComment -> comment.id.countDistinct().desc();
        };
    }
}