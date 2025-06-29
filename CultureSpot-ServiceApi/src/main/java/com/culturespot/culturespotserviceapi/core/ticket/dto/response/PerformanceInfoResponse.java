package com.culturespot.culturespotserviceapi.core.ticket.dto.response;

public record PerformanceInfoResponse(
        Long eventId,
        String eventTitle,
        String event,
        String category,
        String place,
        String imageUrl
) {
}
