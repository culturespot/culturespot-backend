package com.culturespot.culturespotserviceapi.core.performance.service;

import com.culturespot.culturespotdomain.core.performance.entity.Category;
import com.culturespot.culturespotdomain.core.performance.entity.Event;
import com.culturespot.culturespotdomain.core.performance.entity.Performance;
import com.culturespot.culturespotdomain.core.performance.repository.PerformanceLikeRepository;
import com.culturespot.culturespotdomain.core.performance.repository.PerformanceRepository;
import com.culturespot.culturespotdomain.core.performance.entity.Sort;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.core.performance.dto.response.PerformanceListResponse;
import com.culturespot.culturespotserviceapi.core.performance.dto.response.PerformanceListSimpleResponse;
import com.culturespot.culturespotserviceapi.core.performance.dto.response.PerformanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PerformanceService {
    private final PerformanceRepository performanceRepository;
    private final PerformanceLikeRepository likeRepository;

    public PerformanceListResponse getPerformances(User user, Event event, Category category, Sort sort, int size, Long lastId, String keyword){

        Pageable pageable = PageRequest.of(0, size);
        Page<Performance> performances = performanceRepository.findLatestPerformances(event, category, keyword, lastId, pageable);

        Set<Long> likedIds = getLikedPerformanceIds(user);
        List<PerformanceResponse> responses = mapToResponseList(performances.getContent(), likedIds);

        long newLastId = responses.isEmpty() ? 0 : responses.get(responses.size() - 1).id();
        return new PerformanceListResponse(responses, responses.size(), newLastId);

    }

    public PerformanceListSimpleResponse getPopularPerformances(User user, Event event){
        Pageable pageable = PageRequest.of(0, 20);
        List<Performance> performances = performanceRepository.findPopularPerformances(event, pageable);

        Set<Long> likedIds = getLikedPerformanceIds(user);
        List<PerformanceResponse> responses = mapToResponseList(performances, likedIds);

        return new PerformanceListSimpleResponse(responses);
    }

    public PerformanceListSimpleResponse getRecommendedPerformances(User user, Event event) {
        Pageable pageable = PageRequest.of(0, 20);
        List<Category> preferredCategories = user.getPreferredCategory().getOrDefault("select", List.of());

        List<Performance> performances;
        if (!preferredCategories.isEmpty()){
            performances = performanceRepository.findPopularPerformancesByCategories(preferredCategories, event, pageable);
        } else {
            // 카테고리 미선택 시 랜덤 추천
            performances = performanceRepository.findRandomPerformances(event, pageable);
        }

        Set<Long> likedIds = getLikedPerformanceIds(user);
        List<PerformanceResponse> responses = mapToResponseList(performances, likedIds);

        return new PerformanceListSimpleResponse(responses);
    }


    // 로그인한 유저가 좋아요 누른 Performance id 목록 조회(비로그인 시 빈 Set)
    private Set<Long> getLikedPerformanceIds(User user){
        return user != null ? likeRepository.findLikedPerformanceIdsByUserId(user.getId()) : Collections.emptySet();
    }

    // Performance → PerformanceResponse 변환
    private List<PerformanceResponse> mapToResponseList(List<Performance> performances, Set<Long> likedIds) {
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
                        likedIds.contains(p.getId())
                ))
                .toList();
    }
}
