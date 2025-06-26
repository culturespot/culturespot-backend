package com.culturespot.culturespotserviceapi.core.performance.controller;

import com.culturespot.culturespotdomain.core.performance.entity.Category;
import com.culturespot.culturespotdomain.core.performance.entity.Event;
import com.culturespot.culturespotdomain.core.performance.entity.Sort;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.common.security.endpoint.EndpointType;
import com.culturespot.culturespotserviceapi.core.auth.annotation.Auth;
import com.culturespot.culturespotserviceapi.core.performance.dto.response.PerformanceDetailResponse;
import com.culturespot.culturespotserviceapi.core.performance.dto.response.PerformanceListResponse;
import com.culturespot.culturespotserviceapi.core.performance.dto.response.PerformanceListSimpleResponse;
import com.culturespot.culturespotserviceapi.core.performance.service.PerformanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "이벤트 API")
@RestController
@RequiredArgsConstructor
public class PerformanceController{

    private final PerformanceService performanceService;

    @Operation(summary = "이벤트 목록 조회")
    @GetMapping("/api/events")
    public PerformanceListResponse getPerformances(
            @Auth User user,
            @Parameter(description = "이벤트 대분류") @RequestParam(required = false) Event event,
            @Parameter(description = "이벤트 중분류") @RequestParam(required = false) Category category,
            @RequestParam(required = false) Sort sort,
            @RequestParam int size,
            @RequestParam(required = false) Long lastId,
            @RequestParam(required = false) String keyword
    ) {
        return performanceService.getPerformances(user, event, category, sort, size, lastId, keyword);
    }

    @Operation(summary = "실시간 인기 이벤트 조회")
    @GetMapping("/api/events/popular")
    public PerformanceListSimpleResponse getPopularPerformances(
            @Auth User user,
            @RequestParam(required = false) Event event
    ) {
        return performanceService.getPopularPerformances(user, event);
    }

    @Operation(summary = "사용자 맞춤 이벤트 조회")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping(EndpointType.USER_PATH + "/events/recommended")
    public PerformanceListSimpleResponse getRecommendedPerformances(
            @Auth User user,
            @RequestParam(required = false) Event event
    ) {
        return performanceService.getRecommendedPerformances(user, event);
    }

    @Operation(summary = "이벤트 상세 정보 조회")
    @GetMapping("/api/events/{eventId}")
    public PerformanceDetailResponse getPerformanceDetail(
            @Auth User user,
            @PathVariable Long eventId
    ){
        return performanceService.getPerformanceDetail(user, eventId);
    }

}
