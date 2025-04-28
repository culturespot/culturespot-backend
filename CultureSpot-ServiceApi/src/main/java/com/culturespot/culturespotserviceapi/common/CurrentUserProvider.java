package com.culturespot.culturespotserviceapi.common;

import com.culturespot.culturespotserviceapi.core.auth.userInfo.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserProvider {

    public Long getCurrentUserId() {
        return ((CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()).getUser().getId();
    }
}