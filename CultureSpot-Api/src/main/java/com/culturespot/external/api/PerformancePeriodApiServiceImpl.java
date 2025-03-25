package com.culturespot.external.api;

import com.culturespot.external.api.dto.PerformanceResponse;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.github.resilience4j.ratelimiter.RateLimiter;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RequiredArgsConstructor
public class PerformancePeriodApiServiceImpl implements PerformancePeriodApiService {

  private final String baseUrl;
  private final String encodedServiceKey;
  private final WebClient webClient;
  private final RateLimiter rateLimiter;
  private final XmlMapper xmlMapper;

  @Override
  public Optional<PerformanceResponse> call(long pageNo, long numOfRows) {
    String url = baseUrl
        + "?serviceKey=" + encodedServiceKey
        + "&PageNo=" + pageNo
        + "&numOfrows=" + numOfRows
        + "&from=" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
    return RateLimiter.decorateSupplier(rateLimiter, () -> doCall(url))
        .get();
  }

  @Override
  public Optional<PerformanceResponse> call(long pageNo, long numOfRows, LocalDate from,
      LocalDate to) {
    if (from.isAfter(to)) {
      log.warn("The `from` date is after the `to` date. Swapping the dates.");
      return Optional.empty();
    }

    String url = baseUrl
        + "?serviceKey=" + encodedServiceKey
        + "&PageNo=" + pageNo
        + "&numOfrows=" + numOfRows
        + "&from=" + from.format(DateTimeFormatter.BASIC_ISO_DATE)
        + "&to=" + to.format(DateTimeFormatter.BASIC_ISO_DATE);

    return RateLimiter.decorateSupplier(rateLimiter, () -> doCall(url))
        .get();
  }

  private Optional<PerformanceResponse> doCall(String url) {
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

      return Optional.ofNullable(xmlMapper.readValue(response.get(), PerformanceResponse.class));
    } catch (Exception e) {
      log.warn("An error occurred while processing the response.", e);
      return Optional.empty();
    }
  }
}
