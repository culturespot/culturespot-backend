package com.culturespot.culturespotdomain.core.user.service;

import com.culturespot.culturespotcommon.global.exception.AuthException;
import com.culturespot.culturespotcommon.global.exception.AuthExceptionCode;
import com.culturespot.culturespotdomain.core.user.entity.SocialLoginType;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotdomain.core.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        System.out.println(".........email......." + email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException(AuthExceptionCode.USER_NOT_FOUND));
    }

    /**
     * 사용자가 존재하지 않으면 새로 생성하고 저장하는 메서드
     * @param email 사용자의 이메일
     * @param authType 소셜 로그인 타입 (GOOGLE, KAKAO 등)
     * @return User 엔티티
     */
    @Override
    public User createUserIfNotExists(String email, SocialLoginType authType) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .email(email)
                            .nickname(email)  // 기본적으로 이메일을 닉네임으로 설정
                            .password(UUID.randomUUID().toString()) // 랜덤 패스워드 설정
                            .authType(authType)
                            .build();
                    return userRepository.save(newUser);  // 저장 후 반환
                });
    }

    @Override
    public Set<String> getRoleNames(User user) {
        return user.getRoles().stream()
                .map(userRole -> userRole.getRole().getRoleType().name())
                .collect(Collectors.toSet());
    }
}
