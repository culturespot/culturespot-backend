package com.culturespot.culturespotdomain.core.community.application.usecase;

import com.culturespot.culturespotdomain.core.community.application.command.UserAndPostIdCommand;
import com.culturespot.culturespotdomain.core.community.application.service.reader.PostLikeReader;
import com.culturespot.culturespotdomain.core.community.application.service.reader.PostProjectionReader;
import com.culturespot.culturespotdomain.core.community.application.service.reader.PostReader;
import com.culturespot.culturespotdomain.core.community.application.service.writer.PostWriter;
import com.culturespot.culturespotdomain.core.community.application.usecase.type.CommunityUseCaseType;
import com.culturespot.culturespotdomain.core.community.domain.entity.Post;
import com.culturespot.culturespotdomain.core.community.infrastructure.query.projection.PostDetailProjection;
import com.culturespot.culturespotdomain.core.global.exception.DomainException;
import com.culturespot.culturespotdomain.core.global.exception.DomainExceptionCode;
import com.culturespot.culturespotdomain.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadPostDetailService implements CommunityUseCase<PostDetailProjection, UserAndPostIdCommand>{

    private final PostReader postReader;
    private final PostWriter postWriter;
    private final PostProjectionReader projectionReader;
    private final PostLikeReader likeReader;

    @Override
    public CommunityUseCaseType getUseCaseType() {
        return CommunityUseCaseType.READ_POST_DETAIL_SERVICE;
    }

    @Override
    @Transactional
    public PostDetailProjection execute(UserAndPostIdCommand command) {
        User viewer = command.user();
        Long postId = command.postId();

        // fetch: 포스트 정보
        Post post = postReader.fetchPost(postId)
                .orElseThrow(() -> new DomainException(DomainExceptionCode.POST_NOT_FOUND));


        // save: 조회수 1 증감
        post.setViewCount(post.getViewCount() + 1);
        postWriter.register(post);

        // fetch: 조회 포스트 상세 데이터 (사용자 좋아요 유무 제외)
        PostDetailProjection postDetailProjection = projectionReader.fetchPostDetailWithoutLiked(postId)
                .orElseThrow(()->new DomainException(DomainExceptionCode.POST_NOT_FOUND));

        // fetch: 사용자 좋아요 유무
        boolean liked = likeReader.hasUserLikedPost(viewer, postId);


        postDetailProjection.setLiked(liked);

        return postDetailProjection;
    }
}
