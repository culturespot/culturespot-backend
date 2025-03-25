package com.culturespot.culturespotdomain.core.performance.repository;

import com.culturespot.culturespotdomain.core.performance.entity.Performance;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {

  List<Performance> findBySeqIn(Collection<String> seqValues);
}
