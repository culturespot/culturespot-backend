package com.culturespot.culturespotserviceapi.core.performance.service;

import com.culturespot.culturespotdomain.core.performance.entity.Category;
import com.culturespot.culturespotdomain.core.performance.entity.Event;
import com.culturespot.culturespotdomain.core.performance.entity.Performance;
import com.culturespot.culturespotdomain.core.performance.repository.PerformanceRepository;
import com.culturespot.culturespotserviceapi.common.dto.Sort;
import com.culturespot.culturespotserviceapi.core.performance.dto.response.PerformanceListResponse;
import com.culturespot.culturespotserviceapi.core.performance.dto.response.PerformanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PerformanceService {
    private final PerformanceRepository performanceRepository;

    public PerformanceListResponse getPerformances(Event event, Category category, Sort sort, int size, Long lastId, String keyword){
        List<Performance> performances = List.of();
        List<PerformanceResponse> performanceResponses = List.of();

        boolean isMainPage = (size == 20); // 메인화면 캐러셀 size 20 고정


        if (isMainPage){
            Pageable pageable = PageRequest.of(0, 20);
            performances = performanceRepository.findMainLatestPerformances(event, pageable).getContent();

            // Performance -> PerformanceResponse 변환
            performanceResponses = performances.stream()
                    .map(performance -> new PerformanceResponse(
                            performance.getId(),
                            performance.getTitle(),
                            performance.getType(),
                            performance.getCategory(),
                            performance.getPlace(),
                            performance.getStartDate(),
                            performance.getEndDate(),
                            performance.getPerformanceInfo() != null ? performance.getPerformanceInfo().getImageUrl() : null,
                            false //liked 정보 아직 없으니 false
                    ))
                    .collect(Collectors.toList());

        }

        long newLastId = performances.isEmpty() ? 0 : performances.get(performances.size() - 1).getId();
        return new PerformanceListResponse(performanceResponses, performanceResponses.size(), newLastId);

    }
}
