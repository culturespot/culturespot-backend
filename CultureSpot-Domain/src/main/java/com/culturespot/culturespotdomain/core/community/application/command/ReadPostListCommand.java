package com.culturespot.culturespotdomain.core.community.application.command;

import com.culturespot.culturespotdomain.core.community.domain.entity.vo.model.PostSortType;
import com.culturespot.culturespotdomain.core.user.entity.User;
import org.springframework.data.domain.Pageable;

public record ReadPostListCommand(
        User user,
        Pageable pageable,
        String keyword,
        PostSortType postSortType
) {
}
