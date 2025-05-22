package com.culturespot.culturespotdomain.core.community.application.usecase;

import com.culturespot.culturespotdomain.core.community.application.usecase.type.CommunityUseCaseType;

public interface CommunityUseCase<T, C> {
    CommunityUseCaseType getUseCaseType();
    T execute(C command);
}
