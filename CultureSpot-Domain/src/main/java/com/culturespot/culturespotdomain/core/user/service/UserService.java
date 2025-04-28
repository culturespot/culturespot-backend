package com.culturespot.culturespotdomain.core.user.service;

import com.culturespot.culturespotdomain.core.performance.entity.Category;
import com.culturespot.culturespotdomain.core.user.entity.SocialLoginType;
import com.culturespot.culturespotdomain.core.user.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService{
    Set<String> getRoleNames(User user);
    User findUserOrThrow(String username);
    User registerUserIfNotExists(String email, SocialLoginType authType);
    User createUser(String email, SocialLoginType authType);
    void updateLastLoginAt(User user);
    User save(User user);
}
