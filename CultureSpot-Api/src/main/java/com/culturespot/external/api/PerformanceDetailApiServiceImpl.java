package com.culturespot.external.api;

import com.culturespot.external.api.dto.PerformanceDetailResponse;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.github.resilience4j.ratelimiter.RateLimiter;
import java.net.URI;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RequiredArgsConstructor
public class PerformanceDetailApiServiceImpl implements PerformanceDetailApiService {

  private final String baseUrl;
  private final String encodedServiceKey;
  private final WebClient webClient;
  private final RateLimiter rateLimiter;
  private final XmlMapper xmlMapper;

  @Override
  public Optional<PerformanceDetailResponse> call(String seq) {
    String url = baseUrl
        + "?serviceKey=" + encodedServiceKey
        + "&seq=" + seq;

    return RateLimiter.decorateSupplier(rateLimiter, () -> doCall(url))
        .get();
  }

  private Optional<PerformanceDetailResponse> doCall(String url) {
    try {
      Optional<String> response = webClient.get()
          .uri(new URI(url))
          .retrieve()
          .bodyToMono(String.class)
          .blockOptional();

      if (response.isEmpty()) {
        log.warn("Received an empty response from the API.");
        return Optional.empty();
      }
      return Optional.ofNullable(
          xmlMapper.readValue(response.get(), PerformanceDetailResponse.class));
    } catch (Exception e) {
      log.warn("An error occurred while processing the response.", e);
      return Optional.empty();
    }
  }
}
