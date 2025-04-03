package com.culturespot.culturespotdomain.core.global.config;

import com.culturespot.culturespotdomain.core.global.jwt.KeyBuilder;
import java.io.FileInputStream;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;

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
//    private String readKeyFromFile(Resource resource) throws Exception {
//        byte[] keyBytes = StreamUtils.copyToByteArray(resource.getInputStream());
//        return new String(keyBytes, StandardCharsets.UTF_8);
//    }
    private String readKeyFromFile(String path) throws Exception {
        // "file:" prefix가 있으면 제거
        if (path.startsWith("file:")) {
            path = path.substring(5);
        }

        try (InputStream inputStream = new FileInputStream(path)) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}


