package com.culturespot.external.api;

import com.culturespot.external.api.dto.PerformanceDetailResponse;
import java.util.Optional;

public interface PerformanceDetailApiService {

  Optional<PerformanceDetailResponse> call(String seq);
}
