package com.culturespot.culturespotdomain.core.global.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.util.Date;
import java.util.Set;

@Component
public class JwtTokenGenerator {
    private final PrivateKey PRIVATE_KEY;
    private final long ACCESS_TOKEN_EXPIRATION;
    private final long REFRESH_TOKEN_EXPIRATION;

    public JwtTokenGenerator(
            final PrivateKey PRIVATE_KEY,
            @Value("${spring.jwt.access-expiration-time}") final long ACCESS_TOKEN_EXPIRATION,
            @Value("${spring.jwt.refresh-expiration-time}") final long REFRESH_TOKEN_EXPIRATION
    ) {
        this.PRIVATE_KEY = PRIVATE_KEY;
        this.ACCESS_TOKEN_EXPIRATION = ACCESS_TOKEN_EXPIRATION * 1000; // 초 단위를 밀리초로 변환
        this.REFRESH_TOKEN_EXPIRATION = REFRESH_TOKEN_EXPIRATION * 1000; // 초 단위를 밀리초로 변환
    }

    public String generateAccessToken(String email, Set<String> roles) {
        return Jwts.builder()
                .setSubject(email)
                .claim("roles", roles)  // 역할 정보 추가
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(PRIVATE_KEY, SignatureAlgorithm.RS256)
                .compact();
    }

    public String generateRefreshToken(String email) {

        String refreshToken = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(PRIVATE_KEY, SignatureAlgorithm.RS256)
                .compact();

        return refreshToken;
    }
}
