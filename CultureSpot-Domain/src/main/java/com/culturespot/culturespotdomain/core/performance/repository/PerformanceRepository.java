package com.culturespot.culturespotdomain.core.performance.repository;

import com.culturespot.culturespotdomain.core.performance.entity.Category;
import com.culturespot.culturespotdomain.core.performance.entity.Performance;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {

  List<Performance> findBySeqIn(Collection<String> seqValues);

  List<Performance> findAllByUpdatedAtAfterAndCategoryIsIn(LocalDateTime updatedAt,
      Set<Category> categories);
}
