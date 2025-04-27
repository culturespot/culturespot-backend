package com.culturespot.culturespotserviceapi.core.performance.service;

import com.culturespot.culturespotdomain.core.performance.entity.Performance;
import com.culturespot.culturespotdomain.core.performance.entity.PerformanceLike;
import com.culturespot.culturespotdomain.core.performance.repository.PerformanceLikeRepository;
import com.culturespot.culturespotdomain.core.performance.repository.PerformanceRepository;
import com.culturespot.culturespotdomain.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PerformanceLikeService {

    private final PerformanceLikeRepository likeRepository;
    private final PerformanceRepository performanceRepository;

    @Transactional
    public void like(User user, Long performanceId){
        Performance performance = performanceRepository.findById(performanceId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공연입니다."));

        boolean alreadyLiked = likeRepository.findByUserIdAndPerformanceId(user.getId(), performance.getId()).isPresent();
        if (alreadyLiked){
            throw new IllegalStateException("이미 좋아요를 누른 상태입니다.");
        }
        likeRepository.save(new PerformanceLike(user, performance));
    }

    @Transactional
    public void unlike(User user, Long performanceId){
        Performance performance = performanceRepository.findById(performanceId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공연입니다."));

        PerformanceLike like = likeRepository.findByUserIdAndPerformanceId(user.getId(), performance.getId())
                .orElseThrow(() -> new IllegalStateException("좋아요를 누른 적이 없습니다."));

        likeRepository.delete(like);
    }
}
