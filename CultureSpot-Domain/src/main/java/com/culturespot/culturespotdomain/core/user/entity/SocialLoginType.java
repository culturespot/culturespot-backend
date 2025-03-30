package com.culturespot.culturespotdomain.core.user.entity;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SocialLoginType {
    LOCAL,
    GOOGLE,
    KAKAO;

    // ✅ registrationId -> SocialLoginType 매핑
    private static final Map<String, SocialLoginType> REGISTRATION_ID_MAP =
            Stream.of(values())
                    .collect(Collectors.toMap(
                            type -> type.name().toLowerCase(),
                            type -> type
                    ));

    /**
     * ✅ registrationId를 SocialLoginType으로 변환하는 메서드 (O(1) 성능)
     */
    public static SocialLoginType fromRegistrationId(String registrationId) {
        if (registrationId == null) {
            return LOCAL;
        }
        return REGISTRATION_ID_MAP.getOrDefault(registrationId.toLowerCase(), LOCAL);
    }
}

