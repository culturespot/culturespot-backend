package com.culturespot.culturespotdomain.core.community.infrastructure.mapper;

import com.culturespot.culturespotdomain.core.community.application.command.ReadPostDetailCommand;
import com.culturespot.culturespotdomain.core.community.infrastructure.query.projection.PostDetailProjection;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface CommunityProjectionMapper {

    @Mappings({
            @Mapping(target = "postId", source = "source.readPostContent.postId"),
            @Mapping(target = "title", source = "source.readPostContent.title"),
            @Mapping(target = "content", source = "source.readPostContent.content"),
            @Mapping(target = "createdAt", source = "source.readPostContent.createdAt"),
            @Mapping(target = "updatedAt", source = "source.readPostContent.updatedAt"),

            @Mapping(target = "liked", source = "source.readLikeCount.liked"),
            @Mapping(target = "likeCount", source = "source.readLikeCount.likeCount"),

            @Mapping(target = "commentCount", source = "source.commentCount"),

            @Mapping(target = "userId", source = "source.readPostAuthor.userId"),
            @Mapping(target = "username", source = "source.readPostAuthor.username"),
            @Mapping(target = "profileCode", source = "source.readPostAuthor.profileCode"),

            @Mapping(target = "readStoredImageUrls", expression = "java(mapListOrEmpty(source.readStoredImageUrls()))"),
    })
    PostDetailProjection from(ReadPostDetailCommand source);

    default <T> List<T> mapListOrEmpty(List<T> list) {
        return list == null ? new ArrayList<>() : new ArrayList<>(list);
    }
}
