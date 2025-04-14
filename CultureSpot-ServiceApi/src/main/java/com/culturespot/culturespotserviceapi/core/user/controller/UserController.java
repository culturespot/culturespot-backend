package com.culturespot.culturespotserviceapi.core.user.controller;

import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotdomain.core.user.service.UserService;
import com.culturespot.culturespotserviceapi.core.auth.annotation.Auth;
import com.culturespot.culturespotserviceapi.core.global.utils.response.ApiResponse;
import com.culturespot.culturespotserviceapi.core.global.utils.response.NamedWrapper;
import com.culturespot.culturespotserviceapi.core.user.dto.request.UserProfileRequest;
import com.culturespot.culturespotserviceapi.core.user.dto.response.UserProfileResponse;
import com.culturespot.culturespotserviceapi.core.user.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/profiles")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getUserProfile(@Auth User user) {
        User targetUser = userService.findUserOrThrow(user.getEmail());
        UserProfileResponse response = userMapper.userToUserProfileResponse(targetUser);
        return new NamedWrapper("user", response);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PutMapping("/profiles")
    public void getUserProfiles(
            @Auth User user,
            @Valid @RequestBody UserProfileRequest request
    ) {
        userMapper.applyUserProfileRequestPartially(request, user);
        user.updatePreferredCategory(request.preferredGenres());
        userService.save(user);
    }
}
