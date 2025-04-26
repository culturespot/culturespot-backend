package com.culturespot.culturespotdomain.core.user.service;

import com.culturespot.culturespotdomain.core.global.exception.AuthException;
import com.culturespot.culturespotdomain.core.global.exception.AuthExceptionCode;
import com.culturespot.culturespotdomain.core.role.entity.Role;
import com.culturespot.culturespotdomain.core.role.entity.UserRoleType;
import com.culturespot.culturespotdomain.core.role.repository.RoleRepository;
import com.culturespot.culturespotdomain.core.notification.entity.Notification;
import com.culturespot.culturespotdomain.core.notification.repository.NotificationRepository;
import com.culturespot.culturespotdomain.core.performance.entity.Category;
import com.culturespot.culturespotdomain.core.performance.entity.Performance;
import com.culturespot.culturespotdomain.core.performance.repository.PerformanceRepository;
import com.culturespot.culturespotdomain.core.user.entity.SocialLoginType;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotdomain.core.user.entity.UserRole;
import com.culturespot.culturespotdomain.core.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PerformanceRepository performanceRepository;
    private final NotificationRepository notificationRepository;

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
        // TODO: TBD
        Set<Category> preferredCategories = Set.of(Category.EXHIBITION);
        LocalDateTime lastLoginAt = user.getLastLoginAt();

        List<Performance> updatedPerformances = performanceRepository.findAllByUpdatedAtAfterAndCategoryIsIn(
            lastLoginAt, preferredCategories);

        List<Notification> shouldBeSavedNotifications = Lists.newArrayList();
        for (Performance it : updatedPerformances) {
            PerformanceNotification performanceNotification = PerformanceNotification.builder()
                .performanceId(it.getId())
                .performanceType(it.getType().getValue())
                .performanceCategory(it.getCategory().getName())
                .performanceTitle(it.getTitle())
                .performanceStartDate(it.getStartDate())
                .performanceEndDate(it.getEndDate())
                .performancePlace(it.getPlace())
                .build();

            try {
                Notification notification = Notification.builder()
                    .userId(user.getId())
                    .hasBeenRead(false)
                    .contents(OBJECT_MAPPER.writeValueAsString(performanceNotification))
                    .build();
                shouldBeSavedNotifications.add(notification);
            } catch (JsonProcessingException e) {
                log.warn("Notifications cannot be created. `userId`: {}", user.getId(), e);
            }
        }
        notificationRepository.saveAll(shouldBeSavedNotifications);

        userRepository.updateLastLoginAt(user.getId(), LocalDateTime.now());
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class PerformanceNotification {

        private Long performanceId;
        private String performanceType;
        private String performanceCategory;
        private String performanceTitle;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate performanceStartDate;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate performanceEndDate;

        private String performancePlace;
    }
}
