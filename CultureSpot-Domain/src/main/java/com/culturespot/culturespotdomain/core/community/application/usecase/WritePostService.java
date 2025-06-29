package com.culturespot.culturespotdomain.core.community.application.usecase;

import com.culturespot.culturespotdomain.core.community.application.command.CreatePostCommand;
import com.culturespot.culturespotdomain.core.community.application.service.writer.PostWriter;
import com.culturespot.culturespotdomain.core.community.application.usecase.type.CommunityUseCaseType;
import com.culturespot.culturespotdomain.core.community.domain.entity.Post;
import com.culturespot.culturespotdomain.core.community.infrastructure.query.projection.PostIdProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WritePostService implements CommunityUseCase<PostIdProjection, CreatePostCommand> {

    private final PostWriter postWriter;

    @Override
    public CommunityUseCaseType getUseCaseType() {
        return CommunityUseCaseType.WRITE_POST_SERVICE;
    }

    @Override
    @Transactional
    public PostIdProjection execute(CreatePostCommand command) {
        Post post = postWriter.registerPost(command.user(), command.title(), command.content(), command.images());
        return new PostIdProjection(command.user(), post.getId());
    }
}
