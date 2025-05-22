package com.culturespot.culturespotserviceapi.core.community.api.facade;

import com.culturespot.culturespotdomain.core.community.application.command.DeletePostCommand;
import com.culturespot.culturespotdomain.core.community.application.command.UserAndPostIdCommand;
import com.culturespot.culturespotdomain.core.community.application.command.CreatePostCommand;
import com.culturespot.culturespotdomain.core.community.application.command.ReadPostListCommand;
import com.culturespot.culturespotdomain.core.community.application.usecase.CommunityUseCase;
import com.culturespot.culturespotdomain.core.community.application.usecase.type.CommunityUseCaseType;
import com.culturespot.culturespotdomain.core.community.domain.entity.vo.model.PostSortType;
import com.culturespot.culturespotdomain.core.community.infrastructure.query.projection.PostDetailProjection;
import com.culturespot.culturespotdomain.core.community.infrastructure.query.projection.PostIdProjection;
import com.culturespot.culturespotdomain.core.community.infrastructure.query.projection.PostListProjection;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.core.community.api.dto.request.WriteCommunityPostRequest;
import com.culturespot.culturespotserviceapi.core.community.api.dto.response.CommunityPostListResponse;
import com.culturespot.culturespotserviceapi.core.community.api.dto.response.CommunityPostResponse;
import com.culturespot.culturespotserviceapi.core.community.api.mapper.CommunityDtoMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CommunityFacadeImpl implements CommunityFacade {

    private final Map<CommunityUseCaseType, CommunityUseCase<?, ?>> useCaseMap;
    private final CommunityDtoMapper dtoMapper;

    public CommunityFacadeImpl(List<CommunityUseCase<?, ?>> useCases,
                               CommunityDtoMapper dtoMapper
    ) {
        this.useCaseMap = useCases.stream()
                .collect(Collectors.toMap(CommunityUseCase::getUseCaseType, Function.identity()));

        this.dtoMapper = dtoMapper;
    }

    @SuppressWarnings("unchecked")
    private <T, C> CommunityUseCase<T, C> getTypedUseCase(CommunityUseCaseType type) {
        return (CommunityUseCase<T, C>) useCaseMap.get(type);
    }

    @Override
    public CommunityPostResponse loadByCommunityPostDetail(User user, Long postId) {

        CommunityUseCase<PostDetailProjection, UserAndPostIdCommand> useCase =
                getTypedUseCase(CommunityUseCaseType.READ_POST_DETAIL_SERVICE);

        UserAndPostIdCommand command = dtoMapper.to(user, postId); // command from (user, postId)

        PostDetailProjection projection = useCase.execute(command); // projection from command

        return dtoMapper.from(projection);
    }

    @Override
    public Map<String, Long> registerCommunityPost(User user, WriteCommunityPostRequest request) {

        CommunityUseCase<PostIdProjection, CreatePostCommand> useCase =
                getTypedUseCase(CommunityUseCaseType.WRITE_POST_SERVICE);

        CreatePostCommand command = dtoMapper.to(user, request);

        PostIdProjection projection = useCase.execute(command);

        Map<String, Long> result = new HashMap<>();
        result.put("postId", projection.getPostId());

        return result;
    }

    @Override
    public CommunityPostListResponse loadByCommunityPosts(
            User user,
            String keyword,
            PostSortType sortType,
            Pageable pageable
    ) {

        CommunityUseCase<PostListProjection, ReadPostListCommand> useCase =
                getTypedUseCase(CommunityUseCaseType.READ_POST_LIST_SERVICE);

        ReadPostListCommand command = dtoMapper.to(user, keyword, sortType, pageable);

        PostListProjection projection = useCase.execute(command);

        CommunityPostListResponse response = dtoMapper.from(projection);

        return dtoMapper.from(projection);
    }

    @Override
    public void deleteCommunityPost(User user, Long postId) {
        CommunityUseCase<PostListProjection, UserAndPostIdCommand> useCase =
                getTypedUseCase(CommunityUseCaseType.DELETE_POST_SERVICE);

        UserAndPostIdCommand command = dtoMapper.to(user, postId);

        useCase.execute(command);

    }


}
