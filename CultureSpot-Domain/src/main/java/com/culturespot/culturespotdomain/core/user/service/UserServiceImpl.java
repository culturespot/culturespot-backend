package com.culturespot.culturespotdomain.core.user.service;

import com.culturespot.culturespotdomain.core.global.exception.AuthException;
import com.culturespot.culturespotdomain.core.global.exception.AuthExceptionCode;
import com.culturespot.culturespotdomain.core.user.entity.SocialLoginType;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotdomain.core.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User findUserOrThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException(AuthExceptionCode.USER_NOT_FOUND));
    }

    /**
     * 사용자가 존재하지 않으면 새로 생성하고 저장
     *
     * @param email 사용자의 이메일
     * @param authType 소셜 로그인 타입 (예: {@link SocialLoginType#GOOGLE}, {@link SocialLoginType#KAKAO})
     * @return 생성되거나 기존에 존재하던 {@link User} 객체
     */
    @Override
    public User createUserIfNotExists(String email, SocialLoginType authType) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = createUser(email, authType);
                    return userRepository.save(newUser);  // 저장 후 반환
                });
    }

    @Override
    public User createUser(String email, SocialLoginType authType) {
        return User.builder()
                .email(email)
                .nickname(email)  // 기본적으로 이메일을 닉네임으로 설정
                .password(UUID.randomUUID().toString()) // 랜덤 패스워드 설정
                .authType(authType)
                .lastLoginAt(LocalDateTime.now())
                .build();
    }


    /**
     *  사용자가 갖고 있는 권한 조회
     * <p>
     * 권한 이름은 {@link com.culturespot.culturespotdomain.core.role.entity.UserRoleType} enum의 name() 값을 기준으로 하며,
     * 중복 없이 {@link Set} 형태로 반환됩니다.
     * </p>
     * @param user 사용자 정보를 담고 있는 {@link User} 객체
     * @return 사용자가 가진 권한 이름의 집합 (예: "USER", "ADMIN")
     * */
    @Override
    public Set<String> getRoleNames(User user) {
        return user.getRoles().stream()
                .map(userRole -> userRole.getRole().getRoleType().name())
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void updateLastLoginAt(User user) {
        userRepository.updateLastLoginAt(user.getId(), LocalDateTime.now());
    }
}
