package com.culturespot.culturespotdomain.core.global.config;

import com.culturespot.culturespotdomain.core.global.jwt.KeyBuilder;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StreamUtils;

@Slf4j
@Configuration
public class KeyConfig {

    @Value("${spring.jwt.public-key-pem}")
    private String publicKeyPath;

    @Value("${spring.jwt.private-key-pem}")
    private String privateKeyPath;

    @Bean
    public PublicKey publicKey() throws Exception {
        String base64PublicKey = readKeyFromFile(publicKeyPath);
        return KeyBuilder.builder()
                .setPublicKey(base64PublicKey)
                .build()
                .getPublicKey();
    }

    @Bean
    public PrivateKey privateKey() throws Exception {
        String base64PrivateKey = readKeyFromFile(privateKeyPath);
        return KeyBuilder.builder()
                .setPrivateKey(base64PrivateKey)
                .build()
                .getPrivateKey();
    }

    /**
     * 파일에서 Base64로 인코딩된 키 값을 읽어오는 메서드
     */
    private String readKeyFromFile(String path) throws Exception {
        try (InputStream is = new URL(path).openStream()) {
            byte[] keyBytes = StreamUtils.copyToByteArray(is);
            return new String(keyBytes, StandardCharsets.UTF_8);
        }
    }
}