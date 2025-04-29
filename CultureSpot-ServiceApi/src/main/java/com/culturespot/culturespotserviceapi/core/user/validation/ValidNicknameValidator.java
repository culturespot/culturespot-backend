package com.culturespot.culturespotserviceapi.core.user.validation;

import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotdomain.core.user.repository.UserRepository;
import com.culturespot.culturespotserviceapi.common.CurrentUserProvider;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 *  사용자 닉네임의 중복 여부를 검증합니다.
 *  <p>
 *      본인이
 *  </p>
 * */
@Component
@Slf4j
@RequiredArgsConstructor
public class ValidNicknameValidator implements ConstraintValidator<ValidNickname, String> {

    private final UserRepository userRepository;
    private final CurrentUserProvider currentUserProvider;

    @Override
    public boolean isValid(String nickname, ConstraintValidatorContext context) {
        if (nickname == null || nickname.isBlank()) {
            return true;
        }

        Long currentUserId = currentUserProvider.getCurrentUserId();

        Optional<User> userWithSameNickname = userRepository.findByNickname(nickname);

        return userWithSameNickname
                .map(user -> user.getId().equals(currentUserId))
                .orElse(true);
    }
}
