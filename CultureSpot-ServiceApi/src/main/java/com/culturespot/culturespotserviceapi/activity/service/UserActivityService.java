package com.culturespot.culturespotserviceapi.activity.service;

import com.culturespot.culturespotdomain.core.performance.entity.Performance;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.core.performance.dto.response.PerformanceResponse;
import com.culturespot.culturespotserviceapi.core.performance.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserActivityService {

    private final PerformanceService performanceService;

    public List<PerformanceResponse> getLikedPerformances(User user) {
        List<Performance> performances = performanceService.getPerformancesLikedByUser(user.getId());

        return performances.stream()
                .map(p -> new PerformanceResponse(
                        p.getId(),
                        p.getTitle(),
                        p.getType(),
                        p.getCategory(),
                        p.getPlace(),
                        p.getStartDate(),
                        p.getEndDate(),
                        p.getPerformanceInfo() != null ? p.getPerformanceInfo().getImageUrl() : null,
                        true
                ))
                .toList();
    }
}
