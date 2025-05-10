package com.culturespot.culturespotserviceapi.core.performance.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record PerformanceDetailResponse(
        @Schema(description = "공연 상세 정보")
        PerformanceDetail event
) {
}
