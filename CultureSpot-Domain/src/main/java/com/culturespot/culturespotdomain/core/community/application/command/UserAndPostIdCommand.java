package com.culturespot.culturespotdomain.core.community.application.command;

import com.culturespot.culturespotdomain.core.user.entity.User;

public record UserAndPostIdCommand(
    User user,
    Long postId
){
}
