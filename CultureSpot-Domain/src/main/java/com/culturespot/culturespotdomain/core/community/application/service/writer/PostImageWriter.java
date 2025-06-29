package com.culturespot.culturespotdomain.core.community.application.service.writer;

import com.culturespot.culturespotdomain.core.community.domain.entity.PostImage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PostImageWriter {

    public static PostImage of(MultipartFile image) {
        String uploadFileName = image.getOriginalFilename();
        String storedFileName = PostImage.generatedStoredFileName();

       return PostImage.builder()
                .uploadFileName(uploadFileName)
                .storedFileName(storedFileName)
                .build();
    }

}
