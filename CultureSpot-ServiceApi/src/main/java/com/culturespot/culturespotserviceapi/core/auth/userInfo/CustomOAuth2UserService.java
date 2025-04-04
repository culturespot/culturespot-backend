package com.culturespot.culturespotserviceapi.core.auth.userInfo;

import com.culturespot.culturespotdomain.core.role.entity.Role;
import com.culturespot.culturespotdomain.core.role.entity.UserRoleType;
import com.culturespot.culturespotdomain.core.user.entity.SocialLoginType;
import com.culturespot.culturespotdomain.core.user.entity.UserRole;
import com.culturespot.culturespotdomain.core.role.repository.RoleRepository;
import com.culturespot.culturespotdomain.core.user.repository.UserRoleRepository;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotdomain.core.user.repository.UserRepository;
import com.culturespot.culturespotdomain.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    /*
     *  OAuth2 소셜 로그인 사용자 정보를 가져와 회원을 저장합니다.
     * */
    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialLoginType authType = getSocialLoginType(registrationId);

        String email = getEmail(oauth2User, authType);

        // 사용자 조회 또는 생성
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> createNewUser(email, authType));

        // 사용자의 권한 조회 및 GrantedAuthority 변환
        List<GrantedAuthority> authorities = getUserAuthorities(user);

        // `OAuth2User`를 커스텀 객체로 감싸서 반환 (권한 포함)
        return new CustomOAuth2User(oauth2User, email, authorities);
    }

    private User createNewUser(String email, SocialLoginType authType) {
        User createUser = userService.createUser(email, authType);

        // 기본 권한 부여
        Role role = roleRepository.findByRoleType(UserRoleType.USER)
                .orElseThrow(() -> new RuntimeException("ROLE_USER가 존재하지 않습니다."));

        User savedUser = userRepository.save(createUser);

        // 사용자-권한 매핑 저장
        UserRole userRoleMapping = UserRole.builder()
                .user(savedUser)
                .role(role)
                .build();

        userRoleRepository.save(userRoleMapping);

        return savedUser;
    }

    private List<GrantedAuthority> getUserAuthorities(User user) {
        List<UserRole> userRoles = userRoleRepository.findByUser(user);

        return userRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getRoleType().name()))
                .collect(Collectors.toList());
    }

    private SocialLoginType getSocialLoginType(String registrationId) {
        return switch (registrationId.toLowerCase()) {
            case "google" -> SocialLoginType.GOOGLE;
            case "kakao" -> SocialLoginType.KAKAO;
            default -> throw new IllegalArgumentException("지원하지 않는 소셜 로그인: " + registrationId);
        };
    }

    private String getEmail(OAuth2User oauth2User, SocialLoginType authType) {
        return switch (authType) {
            case GOOGLE -> oauth2User.getAttribute("email");
            case KAKAO -> {
                Map<String, Object> kakaoAccount = oauth2User.getAttribute("kakao_account");
                if (kakaoAccount == null || kakaoAccount.get("email") == null) {
                    throw new OAuth2AuthenticationException("카카오 이메일 정보가 없습니다.");
                }
                yield (String) kakaoAccount.get("email");
            }
            default -> throw new IllegalStateException("지원하지 않는 공급자입니다.");
        };
    }
}
