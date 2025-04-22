package com.culturespot.culturespotdomain.core.performance.repository;

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
          WHERE p.type = :event
          ORDER BY p.createdAt DESC
          """)
  Page<Performance> findMainLatestPerformances(@Param("event") Event event, Pageable pageable);

}
