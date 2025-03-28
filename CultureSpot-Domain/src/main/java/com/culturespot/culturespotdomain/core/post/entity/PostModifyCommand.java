package com.culturespot.culturespotdomain.core.post.entity;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record PostModifyCommand(
        String title,
        String content,
        List<MultipartFile> imagesToAdd,
        List<Long> imageIdsToDelete
) {}