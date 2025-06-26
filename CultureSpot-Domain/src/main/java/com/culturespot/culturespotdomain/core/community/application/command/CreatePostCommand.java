package com.culturespot.culturespotdomain.core.community.application.command;

import com.culturespot.culturespotdomain.core.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record CreatePostCommand(
        User user,
        String title,
        String content,
        List<MultipartFile> images
){
}
