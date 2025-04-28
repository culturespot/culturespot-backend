package com.culturespot.culturespotserviceapi.core.user.dto.request;

import com.culturespot.culturespotdomain.core.performance.entity.Category;
import com.culturespot.culturespotserviceapi.core.user.validation.ValidNickname;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * 사용자 프로필 요청 DTO입니다.
 * <p>
 * 프론트에서 전달된 값은 다음과 같이 매핑됩니다:
 * <ul>
 *     <li><b>username</b>: {@link com.culturespot.culturespotdomain.core.user.entity.User#nickname}</li>
 *     <li><b>profileCode</b>: 사용자 프로필 코드</li>
 *     <li><b>preferredGenres</b>: {@link Category}의 name 필드 목록</li>
 * </ul>
 * </p>
 *
 * @param username 사용자 닉네임 (User.nickname과 매핑)
 * @param profileCode 프로필 코드
 * @param preferredGenres 선호 장르 목록 (Category.name 기준)
 */
public record UserProfileRequest (

        @NotBlank
        @ValidNickname(message = "이미 사용중인 닉네임입니다.")
        String username,

        int profileCode,

        List<String> preferredGenres
) {
}


