package com.culturespot.culturespotserviceapi.core.post.dto.request;

import com.culturespot.culturespotserviceapi.core.image.dto.request.ImageDetailRequest;
import com.culturespot.culturespotserviceapi.core.post.dto.PostDetails;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record PostModifyRequest(
        @JsonUnwrapped
        PostDetails postDetails,
        List<MultipartFile> images,
        List<ImageDetailRequest> deleteImage
) {

}