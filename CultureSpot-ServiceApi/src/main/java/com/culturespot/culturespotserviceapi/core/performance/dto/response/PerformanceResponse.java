package com.culturespot.culturespotserviceapi.core.performance.dto.response;

import com.culturespot.culturespotdomain.core.performance.entity.Category;
import com.culturespot.culturespotdomain.core.performance.entity.Event;

import java.time.LocalDate;

public record PerformanceResponse(
        Long id,
        String title,
        Event event,
        Category category,
        String place,
        LocalDate startDate,
        LocalDate endDate,
        String imageUrl,
        boolean liked
) {
}
