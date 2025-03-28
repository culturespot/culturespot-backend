package com.culturespot.culturespotserviceapi.core.post.mapper;

import com.culturespot.culturespotdomain.core.post.entity.Post;
import com.culturespot.culturespotdomain.core.post.entity.PostModifyCommand;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.core.post.dto.PostDetails;
import com.culturespot.culturespotserviceapi.core.post.dto.request.PostCreateRequest;
import com.culturespot.culturespotserviceapi.core.post.dto.request.PostModifyRequest;
import com.culturespot.culturespotserviceapi.core.post.dto.response.PostDetailsResponse;
import com.culturespot.culturespotserviceapi.core.post.dto.response.PostSingleResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public Post toEntity(User user, PostCreateRequest request) {
        return Post.builder()
                .user(user)
                .title(request.post().title())
                .content(request.post().content())
                .images(new ArrayList<>())
                .build();
    }

    public PostModifyCommand toModifyCommand(PostModifyRequest request) {
        PostDetails details = request.postDetails();
        List<Long> deleteImageIds = new ArrayList<>();

        return new PostModifyCommand(
                details.title(),
                details.content(),
                request.images(),
                deleteImageIds
        );
    }

    public PostSingleResponse toResponse(User user, Post post) {
        PostDetailsResponse response = PostDetailsResponse.builder()
                .nickname(user.getNickname())
                .createdAt(post.getCreatedAt())
                .viewCount(post.getViewCount())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
        return PostSingleResponse.builder()
                .postDetails(response)
//                .images() // TODO 추후 관련 작업 시 보완
                .build();
    }
}