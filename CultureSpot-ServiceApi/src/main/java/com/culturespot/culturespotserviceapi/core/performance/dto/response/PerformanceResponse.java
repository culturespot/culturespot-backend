package com.culturespot.culturespotserviceapi.core.performance.dto.response;

import com.culturespot.culturespotdomain.core.performance.entity.Category;
import com.culturespot.culturespotdomain.core.performance.entity.Event;

import java.time.Instant;

public record PerformanceResponse(
        int id,
        String title,
        Event eventType,
        Category category,
        String place,
        Instant startDate,
        Instant endDate,
        String imageUrl,
        boolean liked
) {
}
