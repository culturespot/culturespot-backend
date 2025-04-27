package com.culturespot.culturespotdomain.core.performance.repository;

import com.culturespot.culturespotdomain.core.performance.entity.PerformanceLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PerformanceLikeRepository extends JpaRepository<PerformanceLike, Long> {

    @Query("""
    SELECT pl FROM PerformanceLike pl
    WHERE pl.user.id = :userId AND pl.performance.id = :performanceId
""")
    Optional<PerformanceLike> findByUserIdAndPerformanceId(
            @Param("userId") Long userId,
            @Param("performanceId") Long performanceId
    );

}
