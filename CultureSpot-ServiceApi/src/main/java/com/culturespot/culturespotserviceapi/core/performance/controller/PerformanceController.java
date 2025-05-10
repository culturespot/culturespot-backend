package com.culturespot.culturespotserviceapi.core.performance.controller;

import com.culturespot.culturespotdomain.core.performance.entity.Category;
import com.culturespot.culturespotdomain.core.performance.entity.Event;
import com.culturespot.culturespotdomain.core.performance.entity.Sort;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.common.security.endpoint.EndpointType;
import com.culturespot.culturespotserviceapi.core.auth.annotation.Auth;
import com.culturespot.culturespotserviceapi.core.performance.controller.spec.PerformanceControllerSpec;
import com.culturespot.culturespotserviceapi.core.performance.dto.response.PerformanceDetailResponse;
import com.culturespot.culturespotserviceapi.core.performance.dto.response.PerformanceListResponse;
import com.culturespot.culturespotserviceapi.core.performance.dto.response.PerformanceListSimpleResponse;
import com.culturespot.culturespotserviceapi.core.performance.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PerformanceController implements PerformanceControllerSpec {

    private final PerformanceService performanceService;

    @GetMapping("/api/events")
    public PerformanceListResponse getPerformances(
            @Auth User user,
            @RequestParam(required = false) Event event,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) Sort sort,
            @RequestParam int size,
            @RequestParam(required = false) Long lastId,
            @RequestParam(required = false) String keyword
    ) {
        return performanceService.getPerformances(user, event, category, sort, size, lastId, keyword);
    }

    @GetMapping("/api/events/popular")
    public PerformanceListSimpleResponse getPopularPerformances(
            @Auth User user,
            @RequestParam(required = false) Event event
    ) {
        return performanceService.getPopularPerformances(user, event);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping(EndpointType.USER_PATH + "/events/recommended")
    public PerformanceListSimpleResponse getRecommendedPerformances(
            @Auth User user,
            @RequestParam(required = false) Event event
    ) {
        return performanceService.getRecommendedPerformances(user, event);
    }

    @GetMapping("/api/events/{eventId}")
    public PerformanceDetailResponse getPerformanceDetail(
            @Auth User user,
            @PathVariable Long eventId
    ){
        return performanceService.getPerformanceDetail(user, eventId);
    }

}
