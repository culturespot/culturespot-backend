package com.culturespot.culturespotdomain.core.user.service;

import com.culturespot.culturespotdomain.core.user.entity.SocialLoginType;
import com.culturespot.culturespotdomain.core.user.entity.User;

import java.util.Set;

public interface UserService{
    Set<String> getRoleNames(User user);
    User findUserOrThrow(String username);
    User createUserIfNotExists(String email, SocialLoginType authType);
}
