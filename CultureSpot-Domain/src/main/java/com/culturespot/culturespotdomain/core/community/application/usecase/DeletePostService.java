package com.culturespot.culturespotdomain.core.community.application.usecase;

import com.culturespot.culturespotdomain.core.community.application.command.UserAndPostIdCommand;
import com.culturespot.culturespotdomain.core.community.application.service.reader.PostReader;
import com.culturespot.culturespotdomain.core.community.application.usecase.type.CommunityUseCaseType;
import com.culturespot.culturespotdomain.core.community.domain.entity.Post;
import com.culturespot.culturespotdomain.core.community.infrastructure.persistence.PostRepository;
import com.culturespot.culturespotdomain.core.global.exception.DomainException;
import com.culturespot.culturespotdomain.core.global.exception.DomainExceptionCode;
import com.culturespot.culturespotdomain.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeletePostService implements CommunityUseCase<Void, UserAndPostIdCommand> {

    private final PostReader postReader;
    private final PostRepository postRepository;

    @Override
    public CommunityUseCaseType getUseCaseType() {
        return CommunityUseCaseType.DELETE_POST_SERVICE;
    }

    @Override
    @Transactional
    public Void execute(UserAndPostIdCommand command) {
        User user = command.user();
        Long postId = command.postId();

        Post fetchPost = postReader.fetchPost(postId)
                .orElseThrow(() -> new DomainException(DomainExceptionCode.POST_NOT_FOUND));

        if (!fetchPost.getUser().getId().equals(user.getId())) {
            throw new DomainException(DomainExceptionCode.POST_EDIT_PERMISSION_DENIED);
        }

        postRepository.deleteById(postId);
        return null;
    }
}
