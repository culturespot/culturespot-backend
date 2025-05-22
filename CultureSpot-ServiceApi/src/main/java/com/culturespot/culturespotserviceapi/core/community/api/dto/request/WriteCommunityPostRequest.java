package com.culturespot.culturespotserviceapi.core.community.api.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record WriteCommunityPostRequest(
        String title,
        String content,
        List<MultipartFile> images
){
}
