package com.culturespot.culturespotserviceapi.core.auth.controller;

import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.core.auth.annotation.Auth;
import com.culturespot.culturespotserviceapi.core.auth.annotation.MemberOnly;
import com.culturespot.culturespotserviceapi.core.global.security.endpoint.EndpointType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping(EndpointType.PUBLIC_PATH + "/test")
    public String test() {

        return "test";
    }

    /* /api/users/myInfo */
    @GetMapping(EndpointType.PUBLIC_PATH + "/myInfo")
    public String testUserAuthenticated(
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: " + authentication);
        System.out.println("Authentication: " + authentication);
        System.out.println("Authentication: " + authentication);
        System.out.println("Authentication: " + authentication);
        System.out.println("Authentication: " + authentication);
        System.out.println("Authentication: " + authentication);

        return "testUserAuthenticated";
    }

    @MemberOnly
    @GetMapping(EndpointType.USER_PATH + "/myInfos")
    public String testUserAuthenticated(
            @Auth User user
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: " + authentication);
        System.out.println("Authentication: " + authentication);
        System.out.println("Authentication: " + authentication);
        System.out.println("Authentication: " + authentication);
        System.out.println("Authentication: " + authentication);
        System.out.println("Authentication: " + authentication);

        return "testUserAuthenticated";
    }
}
