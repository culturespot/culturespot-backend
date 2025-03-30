package com.culturespot.external.api;

import com.culturespot.external.api.dto.PerformanceResponse;
import java.time.LocalDate;
import java.util.Optional;

public interface PerformancePeriodApiService {

  Optional<PerformanceResponse> call(long pageNo, long numOfRows);

  Optional<PerformanceResponse> call(long pageNo, long numOfRows, LocalDate from, LocalDate to);
}
