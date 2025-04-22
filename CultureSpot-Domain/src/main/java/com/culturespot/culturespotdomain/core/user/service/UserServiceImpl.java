package com.culturespot.culturespotdomain.core.user.service;

import com.culturespot.culturespotdomain.core.global.exception.AuthException;
import com.culturespot.culturespotdomain.core.global.exception.AuthExceptionCode;
import com.culturespot.culturespotdomain.core.notification.entity.Notification;
import com.culturespot.culturespotdomain.core.notification.repository.NotificationRepository;
import com.culturespot.culturespotdomain.core.performance.entity.Category;
import com.culturespot.culturespotdomain.core.performance.entity.Performance;
import com.culturespot.culturespotdomain.core.performance.repository.PerformanceRepository;
import com.culturespot.culturespotdomain.core.user.entity.SocialLoginType;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotdomain.core.user.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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
    private final PerformanceRepository performanceRepository;
    private final NotificationRepository notificationRepository;

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
