package com.culturespot.culturespotserviceapi.core.performance.dto.response;

import com.culturespot.culturespotserviceapi.common.dto.Paging;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record PerformanceListResponse(
        @Schema(description = "공연에 대한 정보")
        List<PerformanceResponse> performances,

        @JsonUnwrapped
        @Schema(description = "페이지 정보")
        Paging pagingInfo
) {
}
