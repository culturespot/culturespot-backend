package com.culturespot.culturespotserviceapi.core.performance.controller;

import com.culturespot.culturespotdomain.core.performance.entity.Category;
import com.culturespot.culturespotdomain.core.performance.entity.Event;
import com.culturespot.culturespotserviceapi.common.dto.Paging;
import com.culturespot.culturespotserviceapi.common.dto.Sort;
import com.culturespot.culturespotserviceapi.common.security.endpoint.EndpointType;
import com.culturespot.culturespotserviceapi.core.performance.controller.spec.PerformanceControllerSpec;
import com.culturespot.culturespotserviceapi.core.performance.dto.response.PerformanceListResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndpointType.EVENTS_PATH)
public class PerformanceController implements PerformanceControllerSpec {

    @GetMapping
    public PerformanceListResponse getPerformances(
            @ParameterObject Paging paging,
            @RequestParam Event event,
            @RequestParam Sort sort,
            @RequestParam Category category) {
        return null;
    }
}
