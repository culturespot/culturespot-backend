package com.culturespot.culturespotserviceapi.core.performance.controller;

import com.culturespot.culturespotdomain.core.performance.entity.Category;
import com.culturespot.culturespotdomain.core.performance.entity.Event;
import com.culturespot.culturespotserviceapi.common.dto.Sort;
import com.culturespot.culturespotserviceapi.common.security.endpoint.EndpointType;
import com.culturespot.culturespotserviceapi.core.performance.controller.spec.PerformanceControllerSpec;
import com.culturespot.culturespotserviceapi.core.performance.dto.response.PerformanceListResponse;
import com.culturespot.culturespotserviceapi.core.performance.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndpointType.EVENTS_PATH)
public class PerformanceController implements PerformanceControllerSpec {

    private final PerformanceService performanceService;

    @GetMapping
    public PerformanceListResponse getPerformances(
            @RequestParam Event event,
            @RequestParam(required = false) Category category,
            @RequestParam Sort sort,
            @RequestParam int size,
            @RequestParam(required = false) Long lastId,
            @RequestParam(required = false) String keyword
            ) {
        return performanceService.getPerformances(event, category, sort, size, lastId, keyword);
    }
}
