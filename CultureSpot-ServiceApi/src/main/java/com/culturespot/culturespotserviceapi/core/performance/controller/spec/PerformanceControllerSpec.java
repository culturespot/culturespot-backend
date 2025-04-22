package com.culturespot.culturespotserviceapi.core.performance.controller.spec;

import com.culturespot.culturespotdomain.core.performance.entity.Category;
import com.culturespot.culturespotserviceapi.common.dto.Sort;
import com.culturespot.culturespotserviceapi.core.performance.controller.spec.description.PerformanceControllerDescription;
import com.culturespot.culturespotserviceapi.core.performance.dto.response.PerformanceListResponse;
import com.culturespot.culturespotdomain.core.performance.entity.Event;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name="PUBLIC-행사정보 API")
public interface PerformanceControllerSpec {
    @Operation(
            summary = "공연 목록 조회",
            description = "공연 목록 조회와 관련한 API입니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = PerformanceControllerDescription.GET_PERFORMACE_200_RESPONSE,
                        content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = PerformanceListResponse.class
                            )
                        )
                    )
            }
    )
    PerformanceListResponse getPerformances(
            @RequestParam Event event,
            @RequestParam Category category,
            @RequestParam Sort sort,
            @RequestParam int size,
            @RequestParam Long lastId,
            @RequestParam(required = false) String keyword
    );
}