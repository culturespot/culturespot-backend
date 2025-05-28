package com.culturespot.culturespotdomain.core.performance.repository;

import com.culturespot.culturespotdomain.core.performance.entity.Performance;
import com.culturespot.culturespotdomain.core.performance.entity.PerformanceLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
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
    Long countByPerformanceId(@Param("performanceId") Long performanceId);

    @Query("""
    SELECT pl.performance
    FROM PerformanceLike pl
    WHERE pl.user.id = :userId
""")
    List<Performance> findPerformancesLikedByUserId(@Param("userId") Long userId);
}
