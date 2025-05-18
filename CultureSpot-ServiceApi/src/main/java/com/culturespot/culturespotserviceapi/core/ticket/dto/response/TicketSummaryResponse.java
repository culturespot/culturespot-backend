package com.culturespot.culturespotserviceapi.core.ticket.dto.response;

import java.time.LocalDate;

public record TicketSummaryResponse(
        Long ticketId,
        LocalDate startDate,
        LocalDate endDate,
        int rating,
        PerformanceInfoResponse eventInfo
) {
}
