package com.culturespot.culturespotserviceapi.core.community.api.mapper;

import com.culturespot.culturespotdomain.core.community.application.command.CreatePostCommand;
import com.culturespot.culturespotdomain.core.community.application.command.UserAndPostIdCommand;
import com.culturespot.culturespotdomain.core.community.application.command.ReadPostListCommand;
import com.culturespot.culturespotdomain.core.community.domain.entity.vo.model.PostSortType;
import com.culturespot.culturespotdomain.core.community.infrastructure.query.projection.PostDetailProjection;
import com.culturespot.culturespotdomain.core.community.infrastructure.query.projection.PostListProjection;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.core.community.api.dto.request.WriteCommunityPostRequest;
import com.culturespot.culturespotserviceapi.core.community.api.dto.response.CommunityPostListResponse;
import com.culturespot.culturespotserviceapi.core.community.api.dto.response.CommunityPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Pageable;

@Mapper(componentModel = "spring")
public interface CommunityDtoMapper {
    // from
    @Mappings({
            @Mapping(source = "request.postId", target = "post.post.postId"),
            @Mapping(source = "request.title", target = "post.post.title"),
            @Mapping(source = "request.content", target = "post.post.content"),
            @Mapping(source = "request.createdAt", target = "post.post.createdAt"),
            @Mapping(source = "request.updatedAt", target = "post.post.updatedAt"),
            @Mapping(source = "request.liked", target = "post.like.liked"),
            @Mapping(source = "request.likeCount", target = "post.like.likeCount"),
            @Mapping(source = "request.commentCount", target = "post.commentCount"),
            @Mapping(source = "request.userId", target = "post.author.userId"),
            @Mapping(source = "request.username", target = "post.author.username"),
            @Mapping(source = "request.profileCode", target = "post.author.profileCode"),
            @Mapping(source = "request.readStoredImageUrls", target = "post.image"),
    })
    CommunityPostResponse from(PostDetailProjection request);

    // todo
    @Mappings({
            @Mapping(target = "page", source = "request.page"),
            @Mapping(target = "maximumSize", source = "request.maximumSize"),
            @Mapping(target = "currentSize", source = "request.currentSize"),
            @Mapping(target = "isFirstPage", source = "request.firstPage"),
            @Mapping(target = "isLastPage", source = "request.lastPage"),
            @Mapping(target = "totalElements", source = "request.totalElements"),
            @Mapping(target = "totalPages", source = "request.totalPages"),
            @Mapping(target = "posts", source = "request.readPostListDetails"),
    })
    CommunityPostListResponse from(PostListProjection request);

    // to
    @Mappings({
            @Mapping(source = "user", target = "user"),
            @Mapping(source = "request.title", target = "title"),
            @Mapping(source = "request.content", target = "content"),
            @Mapping(source = "request.images", target = "images"),
    })
    CreatePostCommand to(User user, WriteCommunityPostRequest request);

    @Mappings({
            @Mapping(source = "user", target = "user"),
            @Mapping(source = "postId", target = "postId"),
    })
    UserAndPostIdCommand to(User user, Long postId);

    ReadPostListCommand to(User user, String keyword, PostSortType sortType, Pageable pageable);
}
