package com.culturespot.culturespotdomain.core.image.service;

import com.culturespot.culturespotdomain.core.image.entity.Image;
import com.culturespot.culturespotdomain.core.post.entity.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    List<Image> createImage(Post post, List<MultipartFile> files);
    void deleteImages(Long id);
    void deleteImages(List<Long> id);
}