package com.culturespot.culturespotserviceapi.core.ticket.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record TicketResponse(
        @Schema(description = "티켓 상세 내용")
        TicketDetailResponse ticket
) {
}
