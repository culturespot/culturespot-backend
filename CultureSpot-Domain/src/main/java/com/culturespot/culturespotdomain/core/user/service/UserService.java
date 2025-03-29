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
     * @param email 사용자의 이메일 주소
     * @return 이메일에 해당하는 {@link User} 객체 반환
     * @throws {@link AuthException} 사용자가 존재하지 않는다면 {@link AuthExceptionCode#USER_NOT_FOUND} 예외를 발생
     */
    User findUserOrThrow(String email);

    /**
     * 사용자가 존재하지 않으면 새로 생성하고 저장하는 메서드
     *
     * @param email 사용자의 이메일
     * @param authType 소셜 로그인 타입 (예: {@link SocialLoginType#GOOGLE}, {@link SocialLoginType#KAKAO})
     * @return 생성되거나 기존에 존재하던 {@link User} 객체
     */
    User createUserIfNotExists(String email, SocialLoginType authType);

    public void updateLastLoginAt(User user);
}
