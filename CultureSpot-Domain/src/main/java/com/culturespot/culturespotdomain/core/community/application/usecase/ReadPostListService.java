package com.culturespot.culturespotdomain.core.community.application.usecase;

import com.culturespot.culturespotdomain.core.community.application.command.ReadPostListCommand;
import com.culturespot.culturespotdomain.core.community.application.usecase.type.CommunityUseCaseType;
import com.culturespot.culturespotdomain.core.community.domain.entity.vo.model.PostSortType;
import com.culturespot.culturespotdomain.core.community.domain.entity.vo.model.read.ReadPostListDetails;
import com.culturespot.culturespotdomain.core.community.infrastructure.query.PostProjectionQuerydsl;
import com.culturespot.culturespotdomain.core.community.infrastructure.query.projection.PostListProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadPostListService implements CommunityUseCase<PostListProjection, ReadPostListCommand> {

    private final PostProjectionQuerydsl postProjectionQuerydsl;

    @Override
    public CommunityUseCaseType getUseCaseType() {
        return CommunityUseCaseType.READ_POST_LIST_SERVICE;
    }

    @Override
    @Transactional(readOnly = true)
    public PostListProjection execute(ReadPostListCommand command) {

        String keyword = command.keyword();
        PostSortType sortType = command.postSortType();
        Pageable pageable = command.pageable();

        Page<ReadPostListDetails> postList = postProjectionQuerydsl.searchPostList(keyword, sortType, pageable);

        return PostListProjection.of(postList);
    }
}
