package com.culturespot.external.api;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

import com.culturespot.external.api.dto.PerformanceResponse;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.internal.AtomicRateLimiter;
import java.util.Optional;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;

//@Ignore
@ExtendWith(RandomBeansExtension.class)
class PerformancePeriodApiServiceImplTest {

  @Mock
  private XmlMapper xmlMapper;

  @InjectMocks
  private PerformancePeriodApiServiceImpl performancePeriodApiService;

  private MockWebServer mockWebServer;
  private WebClient webClient;

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);

    mockWebServer = new MockWebServer();
    mockWebServer.start();

    webClient = WebClient.builder().baseUrl(mockWebServer.url("/").toString()).build();
    performancePeriodApiService = new PerformancePeriodApiServiceImpl(
        "http://localhost:" + mockWebServer.getPort(),
        "encodedServiceKey",
        webClient,
        new AtomicRateLimiter("testAtomicRateLimiter", RateLimiterConfig.custom().build()),
        xmlMapper);

  }

  @AfterEach
  void tearDown() throws Exception {
    mockWebServer.shutdown();
  }

  @Test
  void testCall_normalCase(@Random PerformanceResponse randomResponse) throws Exception {
    // Given
    int pageNo = 1;
    int numOfRows = 10;

    mockWebServer.enqueue(new MockResponse().setBody("<response></response>")
        .addHeader("Content-Type", "application/xml"));
    when(xmlMapper.readValue(anyString(), eq(PerformanceResponse.class)))
        .thenReturn(randomResponse);

    // When
    Optional<PerformanceResponse> response = performancePeriodApiService.call(pageNo, numOfRows);

    // Then
    assertTrue(response.isPresent());
  }

  @Test
  void testCall_responseEmpty() {
    // Given
    int pageNo = 1;
    int numOfRows = 10;

    mockWebServer.enqueue(
        new MockResponse().setBody("").addHeader("Content-Type", "application/xml"));

    // When
    Optional<PerformanceResponse> response = performancePeriodApiService.call(pageNo, numOfRows);

    // Then
    assertTrue(response.isEmpty());
  }

  @Test
  void testCall_parsingException() throws Exception {
    // Given
    int pageNo = 1;
    int numOfRows = 10;

    mockWebServer.enqueue(new MockResponse().setBody("<response></response>")
        .addHeader("Content-Type", "application/xml"));
    when(xmlMapper.readValue(anyString(), eq(PerformanceResponse.class))).thenThrow(
        new RuntimeException("Parsing error"));

    // When
    Optional<PerformanceResponse> response = performancePeriodApiService.call(pageNo, numOfRows);

    // Then
    assertTrue(response.isEmpty());
  }
}