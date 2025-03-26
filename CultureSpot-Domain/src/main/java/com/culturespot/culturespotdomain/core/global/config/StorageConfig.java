package com.culturespot.culturespotdomain.core.global.config;

import com.culturespot.culturespotdomain.core.global.infrastructure.storage.property.MinioProperty;
import com.culturespot.culturespotdomain.core.global.infrastructure.storage.service.MinioStorageService;
import com.culturespot.culturespotdomain.core.global.infrastructure.storage.service.StorageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.minio.MinioClient;

@Configuration
public class StorageConfig {

    @Bean
    @ConditionalOnProperty(name = "storage.type", havingValue = "minio")
    public StorageService minioStorageService(MinioClient minioClient, MinioProperty property) {
        return new MinioStorageService(minioClient, property);
    }

    // 임시로 만든 bean입니다.
    // 차후 s3를 사용할 때 수정해야합니다.
    // 임시로 작성한 이유는, build시 문제가 되었기 때문입니다.
    @Bean
    @ConditionalOnProperty(name = "storage.type", havingValue = "s3")
    public StorageService s3StorageService(MinioClient minioClient, MinioProperty property) {
        return new MinioStorageService(minioClient, property);
    }
}
