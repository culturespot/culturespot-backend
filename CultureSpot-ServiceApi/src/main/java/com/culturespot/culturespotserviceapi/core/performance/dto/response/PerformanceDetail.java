package com.culturespot.culturespotserviceapi.core.performance.dto.response;

import com.culturespot.culturespotdomain.core.performance.entity.Category;
import com.culturespot.culturespotdomain.core.performance.entity.Event;

import java.time.LocalDate;

public record PerformanceDetail(
        Long id,
        String title,
        Event event,
        Category category,
        String place,
        LocalDate startDate,
        LocalDate endDate,
        String address,
        double gpsX,
        double gpsY,
        String price,
        String description,
        String url,
        String imageUrl,
        boolean liked,
        Long likeCount
) {
}
