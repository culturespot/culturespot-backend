package com.culturespot.culturespotserviceapi.core.user.spec;

import com.culturespot.culturespotdomain.core.user.entity.SocialLoginType;
import com.culturespot.culturespotserviceapi.core.global.utils.response.NamedWrapper;
import com.culturespot.culturespotserviceapi.core.user.dto.response.UserProfileResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "`user` 키로 감싸진 유저 응답 구조입니다.",
        example = """
        {
          "user": {
            "userId": 1,
            "username": "euncheol",
            "platform": "KAKAO",
            "email": "test@example.com",
            "profileCode": 10,
            "preferredGenres": ["전시", "음악"]
          }
        }
    """
)
public class SwaggerUserProfileResponse extends NamedWrapper<UserProfileResponse> {

    private final UserProfileResponse userProfileResponse;

    public SwaggerUserProfileResponse(String key, UserProfileResponse value) {
        super(key, value);
        this.userProfileResponse = new UserProfileResponse(
                1L,
                "userNickname",
                SocialLoginType.KAKAO,
                "test@example.com",
                10,
                List.of("음악", "전시")
        );
    }

    public UserProfileResponse getUser() {
        return userProfileResponse;
    }
}
