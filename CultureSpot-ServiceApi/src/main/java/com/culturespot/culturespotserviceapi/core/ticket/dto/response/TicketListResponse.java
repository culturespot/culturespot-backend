package com.culturespot.culturespotserviceapi.core.ticket.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record TicketListResponse(
        @Schema(description = "티켓이 있는 연도 목록 (티켓 startDate 기준)")
        List<Integer> years,
        @Schema(description = "티켓 리스트")
        List<TicketSummaryResponse> ticketbook
) {
}
