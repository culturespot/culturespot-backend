package com.culturespot.culturespotserviceapi.core.global.security.filter;

import com.culturespot.culturespotserviceapi.core.auth.handler.OAuth2AuthenticationFailureHandler;
import com.culturespot.culturespotserviceapi.core.auth.handler.OAuth2AuthenticationSuccessHandler;
import com.culturespot.culturespotserviceapi.core.auth.resolver.CustomOAuth2AuthorizationRequestResolver;
import com.culturespot.culturespotserviceapi.core.auth.userInfo.CustomOAuth2UserService;
import com.culturespot.culturespotserviceapi.core.global.security.config.CorsConfig;
import com.culturespot.culturespotserviceapi.core.global.security.endpoint.EndpointType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityFilter {
    private final OAuth2AuthenticationSuccessHandler oAuth2SuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter,
            ClientRegistrationRepository clientRegistrationRepository
    ) throws Exception {

        // ✅ Custom OAuth2 Authorization Request Resolver 생성
        OAuth2AuthorizationRequestResolver customOAuth2AuthorizationRequestResolver =
                new CustomOAuth2AuthorizationRequestResolver(
                        new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, "/oauth2/authorization")
                );

        // ✅ 보호 비활성화 및 세션 정책 설정
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(CorsConfig.corsConfigurationSource()))
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        // ✅ 엔드포인트 접근 권한 설정
        http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(EndpointType.PUBLIC.getPath()).permitAll();
                    auth.requestMatchers(EndpointType.USER.getPath()).authenticated();
                    auth.requestMatchers(EndpointType.ADMIN.getPath()).hasRole("ADMIN");
                    auth.anyRequest().authenticated();
                });

        // ✅ 인증되지 않은 사용자가 보호된 리소스에 접근 시 403 반환 (리다이렉트 X)
        http
                .exceptionHandling(exception ->
                    exception.authenticationEntryPoint(new Http403ForbiddenEntryPoint())  // 403 Forbidden 반환
                );

        http
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(authEndpoint -> authEndpoint
                                .authorizationRequestResolver(customOAuth2AuthorizationRequestResolver)
                        )
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .successHandler(oAuth2SuccessHandler)
                        .failureHandler(oAuth2AuthenticationFailureHandler)
                );

        // ✅ JWT 인증 필터 추가 (기본 인증 필터보다 먼저 실행됨)
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
