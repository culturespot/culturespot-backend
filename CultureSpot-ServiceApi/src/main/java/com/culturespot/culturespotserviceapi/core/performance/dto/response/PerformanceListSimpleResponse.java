package com.culturespot.culturespotserviceapi.core.performance.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record PerformanceListSimpleResponse(
        @Schema(description = "공연에 대한 정보")
        List<PerformanceResponse> events
) {
}
