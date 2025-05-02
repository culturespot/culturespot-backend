package com.culturespot.culturespotdomain.core.performance.repository;

import com.culturespot.culturespotdomain.core.performance.entity.Category;
import com.culturespot.culturespotdomain.core.performance.entity.Event;
import com.culturespot.culturespotdomain.core.performance.entity.Performance;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {

  List<Performance> findBySeqIn(Collection<String> seqValues);

  @Query("""
    SELECT p FROM Performance p
    WHERE (:event IS NULL OR p.type = :event)
      AND (:category IS NULL OR p.category = :category)
      AND (:keyword IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')))
      AND (:lastId IS NULL OR p.id < :lastId)
    ORDER BY p.createdAt DESC
""")
  Page<Performance> findLatestPerformances(@Param("event") Event event,
                                     @Param("category") Category category,
                                     @Param("keyword") String keyword,
                                     @Param("lastId") Long lastId,
                                     Pageable pageable);

  @Query("""
    SELECT p
    FROM Performance p
    LEFT JOIN PerformanceLike pl ON p.id = pl.performance.id
    WHERE (:event IS NULL OR p.type = :event)
    GROUP BY p.id
    ORDER BY COUNT(pl.id) DESC, p.id DESC
""")
  List<Performance> findPopularPerformances(@Param("event") Event event, Pageable pageable);
}
