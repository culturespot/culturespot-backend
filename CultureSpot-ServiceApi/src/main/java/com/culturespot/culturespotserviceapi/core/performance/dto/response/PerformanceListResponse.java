package com.culturespot.culturespotserviceapi.core.performance.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record PerformanceListResponse(
        @Schema(description = "공연에 대한 정보")
        List<PerformanceResponse> events,

        @Schema(description = "요청한 데이터 개수")
        int size,

        @Schema(description = "마지막 아이템 ID")
        long lastId

) {
}
