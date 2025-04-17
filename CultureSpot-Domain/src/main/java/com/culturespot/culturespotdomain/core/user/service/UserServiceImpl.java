package com.culturespot.culturespotdomain.core.user.service;

import com.culturespot.culturespotdomain.core.global.exception.AuthException;
import com.culturespot.culturespotdomain.core.global.exception.AuthExceptionCode;
import com.culturespot.culturespotdomain.core.global.exception.DomainException;
import com.culturespot.culturespotdomain.core.role.entity.Role;
import com.culturespot.culturespotdomain.core.role.entity.UserRoleType;
import com.culturespot.culturespotdomain.core.role.repository.RoleRepository;
import com.culturespot.culturespotdomain.core.user.entity.SocialLoginType;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotdomain.core.user.entity.UserRole;
import com.culturespot.culturespotdomain.core.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public User findUserOrThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException(AuthExceptionCode.USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public User registerUserIfNotExists(String email, SocialLoginType authType) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User user = createUser(email, authType);
                    return userRepository.save(user);
                });
    }

    @Override
    public User createUser(String email, SocialLoginType authType) {
        User user = User.builder()
                .email(email)
                .nickname(email)
                .authType(authType)
                .lastLoginAt(LocalDateTime.now())
                .profileCode((int)(Math.random() * 10_000) + 1)
                .build();

        Role userRole = roleRepository.findByRoleType(UserRoleType.USER)
                .orElseThrow(() -> new AuthException(AuthExceptionCode.INVALID_ROLE));

        user.addRole(new UserRole(user, userRole));

        return user;
    }

    @Override
    public Set<String> getRoleNames(User user) {
        return user.getRoles().stream()
                .map(userRole -> userRole.getRole().getRoleType().name())
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateLastLoginAt(User user) {
        userRepository.updateLastLoginAt(user.getId(), LocalDateTime.now());
    }
}
