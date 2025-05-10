package com.culturespot.culturespotdomain.core.performance.repository;

import com.culturespot.culturespotdomain.core.performance.entity.PerformanceLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface PerformanceLikeRepository extends JpaRepository<PerformanceLike, Long> {

    @Query("""
    SELECT pl FROM PerformanceLike pl
    WHERE pl.user.id = :userId AND pl.performance.id = :performanceId
""")
    Optional<PerformanceLike> findByUserIdAndPerformanceId(
            @Param("userId") Long userId,
            @Param("performanceId") Long performanceId
    );

    @Query("""
    SELECT pl.performance.id FROM PerformanceLike pl
    WHERE pl.user.id = :userId
""")
    Set<Long> findLikedPerformanceIdsByUserId(@Param("userId") Long userId);


    @Query("""
    SELECT COUNT(pl)
    FROM PerformanceLike pl
    WHERE pl.performance.id = :performanceId
""")
    int countByPerformanceId(@Param("performanceId") Long performanceId);
}
