package com.culturespot.culturespotserviceapi.core.post.dto;

import lombok.Builder;

@Builder
public record PostDetails(
        String title,
        String content
){
}
