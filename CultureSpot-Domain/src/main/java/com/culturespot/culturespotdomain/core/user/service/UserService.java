package com.culturespot.culturespotdomain.core.user.service;

import com.culturespot.culturespotdomain.core.global.exception.AuthException;
import com.culturespot.culturespotdomain.core.global.exception.AuthExceptionCode;
import com.culturespot.culturespotdomain.core.user.entity.SocialLoginType;
import com.culturespot.culturespotdomain.core.user.entity.User;

import java.util.Set;

public interface UserService{
    /**
     *  사용자가 갖고 있는 권한 조회
     * <p>
     * 권한 이름은 {@link com.culturespot.culturespotdomain.core.role.entity.UserRoleType} enum의 name() 값을 기준으로 하며,
     * 중복 없이 {@link Set} 형태로 반환됩니다.
     * </p>
     * @param user 사용자 정보를 담고 있는 {@link User} 객체
     * @return 사용자가 가진 권한 이름의 집합 (예: "USER", "ADMIN")
     * */
    Set<String> getRoleNames(User user);
    /**
     * 주어진 이메일에 해당하는 사용자를 조회
     * <p>
     * 사용자가 존재하면 해당 {@link User} 객체를 반환하고, 존재하지 않으면 {@link AuthException}을 발생시킵니다.
     * </p>
     *
     * @throws {@link AuthException} 사용자가 존재하지 않는다면 {@link AuthExceptionCode#USER_NOT_FOUND} 예외를 발생
     */
    User findUserOrThrow(String email);
    User createUserIfNotExists(String email, SocialLoginType authType);
    User createUser(String email, SocialLoginType authType);
    void updateLastLoginAt(User user);
}
