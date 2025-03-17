package com.culturespot.culturespotdomain.core.global.security.token;

import com.culturespot.culturespotcommon.global.exception.AuthException;
import com.culturespot.culturespotcommon.global.exception.AuthExceptionCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.PublicKey;

@Component
@Slf4j
public class JwtTokenValidator {
    private final PublicKey publicKey;

    public JwtTokenValidator(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public boolean validateAccessToken(String accessToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(accessToken);
            return true;
        } catch (ExpiredJwtException e) {
            throw new AuthException(AuthExceptionCode.EXPIRED_ACCESS_TOKEN);
        } catch (SignatureException e) {
            throw new AuthException(AuthExceptionCode.SIGNATURE_ACCESS_TOKEN);
        } catch (MalformedJwtException e) {
            throw new AuthException(AuthExceptionCode.MALFORMED_ACCESS_TOKEN);
        } catch (Exception e) {
            log.error("❌Unexpected error while validating access token............", e);
            throw new AuthException(AuthExceptionCode.UNKNOWN_ERROR);
        }
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(refreshToken);
            return true;
        } catch (ExpiredJwtException e) {
            throw new AuthException(AuthExceptionCode.EXPIRED_REFRESH_TOKEN);
        } catch (SignatureException e) {
            throw new AuthException(AuthExceptionCode.SIGNATURE_REFRESH_TOKEN);
        } catch (MalformedJwtException e) {
            throw new AuthException(AuthExceptionCode.MALFORMED_REFRESH_TOKEN);
        } catch (Exception e) {
            log.error("❌ Unexpected error while validating refresh token............", e);
            throw new AuthException(AuthExceptionCode.UNKNOWN_ERROR);
        }

    }

    public String getEmailFromAccessToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public String getEmailFromRefreshToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}

