package com.culturespot.culturespotserviceapi.core.ticket.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TicketDetailResponse(
        Long ticketId,
        LocalDate startDate,
        LocalDate endDate,
        int rating,
        String ticketTitle,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        PerformanceInfoResponse eventInfo

) {
}
