package com.culturespot.culturespotdomain.core.community.application.command;

import com.culturespot.culturespotdomain.core.community.domain.entity.vo.model.read.ReadStoredImageUrl;
import com.culturespot.culturespotdomain.core.community.domain.entity.vo.model.read.ReadLikeCount;
import com.culturespot.culturespotdomain.core.community.domain.entity.vo.model.read.ReadPostAuthor;
import com.culturespot.culturespotdomain.core.community.domain.entity.vo.model.read.ReadPostContent;

import java.util.List;

public record ReadPostDetailCommand(

        ReadPostContent readPostContent,

        ReadLikeCount readLikeCount,

        Long commentCount,

        List<ReadStoredImageUrl> readStoredImageUrls,

        ReadPostAuthor readPostAuthor
){
}