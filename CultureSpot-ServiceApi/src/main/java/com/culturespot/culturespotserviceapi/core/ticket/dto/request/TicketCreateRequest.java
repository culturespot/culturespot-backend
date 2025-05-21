package com.culturespot.culturespotserviceapi.core.ticket.dto.request;

import java.time.LocalDate;

public record TicketCreateRequest(
        Long eventId,
        LocalDate startDate,
        LocalDate endDate,
        int rating,
        String title,
        String content
) {
}
