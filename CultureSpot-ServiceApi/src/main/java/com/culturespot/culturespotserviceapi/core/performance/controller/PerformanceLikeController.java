package com.culturespot.culturespotserviceapi.core.performance.controller;

import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.common.security.endpoint.EndpointType;
import com.culturespot.culturespotserviceapi.core.auth.annotation.Auth;
import com.culturespot.culturespotserviceapi.core.performance.service.PerformanceLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndpointType.USER_PATH + "/events")
public class PerformanceLikeController {

    private final PerformanceLikeService likeService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/{eventId}/like")
    public ResponseEntity<?> like(@Auth User user, @PathVariable Long eventId){
        likeService.like(user, eventId);
        return ResponseEntity.ok(Map.of("liked", true));
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/{eventId}/like")
    public ResponseEntity<?> unlike(@Auth User user, @PathVariable Long eventId){
        likeService.unlike(user, eventId);
        return ResponseEntity.ok(Map.of("liked", false));
    }

}
