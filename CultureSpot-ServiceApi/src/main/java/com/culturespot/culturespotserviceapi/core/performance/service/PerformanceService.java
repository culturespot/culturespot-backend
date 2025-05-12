package com.culturespot.culturespotserviceapi.core.performance.service;

import com.culturespot.culturespotdomain.core.performance.entity.Category;
import com.culturespot.culturespotdomain.core.performance.entity.Event;
import com.culturespot.culturespotdomain.core.performance.entity.Performance;
import com.culturespot.culturespotdomain.core.performance.repository.PerformanceLikeRepository;
import com.culturespot.culturespotdomain.core.performance.repository.PerformanceRepository;
import com.culturespot.culturespotdomain.core.performance.entity.Sort;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.core.performance.dto.response.PerformanceListResponse;
import com.culturespot.culturespotserviceapi.core.performance.dto.response.PerformanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PerformanceService {
    private final PerformanceRepository performanceRepository;
    private final PerformanceLikeRepository likeRepository;

    public PerformanceListResponse getPerformances(User user, Event event, Category category, Sort sort, int size, Long lastId, String keyword){

        Pageable pageable = PageRequest.of(0, size);

        // 최신 Performance 목록 조회
        Page<Performance> performances = performanceRepository.findLatestPerformances(event, category, keyword, lastId, pageable);

        // 로그인한 유저가 좋아요 누른 Performance id 목록 조회(비로그인 시 빈 Set)
        Set<Long> likedIds = user != null ? likeRepository.findLikedPerformanceIdsByUserId(user.getId()) : Collections.emptySet();

        // Performance -> PerformanceResponse 변환
        List<PerformanceResponse> responses = performances.stream()
                .map(performance -> new PerformanceResponse(
                        performance.getId(),
                        performance.getTitle(),
                        performance.getType(),
                        performance.getCategory(),
                        performance.getPlace(),
                        performance.getStartDate(),
                        performance.getEndDate(),
                        performance.getPerformanceInfo() != null ? performance.getPerformanceInfo().getImageUrl() : null,
                        likedIds.contains(performance.getId())
                ))
                .collect(Collectors.toList());

        long newLastId = responses.isEmpty() ? 0 : responses.get(responses.size() - 1).id();
        return new PerformanceListResponse(responses, responses.size(), newLastId);

    }
}
