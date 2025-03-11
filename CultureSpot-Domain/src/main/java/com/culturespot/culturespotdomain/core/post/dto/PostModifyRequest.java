package com.culturespot.culturespotdomain.core.post.dto;

import com.culturespot.culturespotdomain.core.image.dto.ImageDetailRequest;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record PostModifyRequest (
        @JsonUnwrapped
        PostDetails postDetails,
        List<MultipartFile> images,
        List<ImageDetailRequest> deleteImage
){
}