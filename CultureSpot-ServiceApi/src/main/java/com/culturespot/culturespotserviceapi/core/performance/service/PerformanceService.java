package com.culturespot.culturespotserviceapi.core.performance.service;

import com.culturespot.culturespotdomain.core.performance.entity.*;
import com.culturespot.culturespotdomain.core.performance.repository.PerformanceLikeRepository;
import com.culturespot.culturespotdomain.core.performance.repository.PerformanceRepository;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.core.performance.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PerformanceService {
    private final PerformanceRepository performanceRepository;
    private final PerformanceLikeRepository likeRepository;

    public PerformanceListResponse getPerformances(User user, Event event, Category category, Sort sort, int size, Long lastId, String keyword){

        Pageable pageable = PageRequest.of(0, size);
        List<Performance> performances = Collections.emptyList();
        Long lastLikeCount = null; // 이전 페이지 마지막 이벤트의 좋아요 수(인기순 정렬에 필요)

        switch (sort != null ? sort : Sort.LATEST){
            case LATEST -> performances = performanceRepository.findLatestPerformances(event, category, keyword, lastId, pageable);
            case OLDEST -> performances = performanceRepository.findOldestPerformances(event, category, keyword, lastId, pageable);
            case POPULAR -> {
                if (lastId != null) {
                    lastLikeCount = likeRepository.countByPerformanceId(lastId);
                }
                performances = performanceRepository.findPopularPerformances(event, category, keyword, lastLikeCount, lastId, pageable);
            }
        }

        Set<Long> likedIds = getLikedPerformanceIds(user);
        List<PerformanceResponse> responses = mapToResponseList(performances, likedIds);

        long newLastId = responses.isEmpty() ? 0 : responses.get(responses.size() - 1).id();
        return new PerformanceListResponse(responses, responses.size(), newLastId);

    }

    public PerformanceListSimpleResponse getPopularPerformances(User user, Event event){
        Pageable pageable = PageRequest.of(0, 20);
        List<Performance> performances = performanceRepository.findPopularPerformancesForMain(event, pageable);

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

    public PerformanceDetailResponse getPerformanceDetail(User user, Long eventId) {
        Performance performance = performanceRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchElementException("해당 공연이 존재하지 않습니다"));

        PerformanceInfo info = performance.getPerformanceInfo();

        Set<Long> likedIds = getLikedPerformanceIds(user);
        boolean liked = likedIds.contains(performance.getId());

        // 총 좋아요 수
        Long likeCount = likeRepository.countByPerformanceId(performance.getId());

        PerformanceDetail detail = new PerformanceDetail(
                performance.getId(),
                performance.getTitle(),
                performance.getType(),
                performance.getCategory(),
                performance.getPlace(),
                performance.getStartDate(),
                performance.getEndDate(),
                performance.getAddress(),
                performance.getGpsX(),
                performance.getGpsY(),
                info != null ? info.getPrice() : null,
                info != null ? info.getDescription() : null,
                info != null ? info.getUrl() : null,
                info != null ? info.getImageUrl() : null,
                liked,
                likeCount
        );

        return new PerformanceDetailResponse(detail);
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
