package com.culturespot.culturespotserviceapi.core.community.api.facade;

import com.culturespot.culturespotdomain.core.community.domain.entity.Post;
import com.culturespot.culturespotdomain.core.community.domain.entity.vo.model.PostSortType;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.core.community.api.dto.request.WriteCommunityPostRequest;
import com.culturespot.culturespotserviceapi.core.community.api.dto.response.CommunityPostListResponse;
import com.culturespot.culturespotserviceapi.core.community.api.dto.response.CommunityPostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface CommunityFacade {
    CommunityPostResponse loadByCommunityPostDetail(User user, Long postId);

    Map<String, Long> registerCommunityPost(User user, WriteCommunityPostRequest request);

    CommunityPostListResponse loadByCommunityPosts(User user, String keyword, PostSortType sortType, Pageable pageable);

    void deleteCommunityPost(User user, Long postId);
}
