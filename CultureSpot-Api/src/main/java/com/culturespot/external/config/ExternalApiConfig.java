package com.culturespot.external.config;

import com.culturespot.external.api.PerformanceDetailApiService;
import com.culturespot.external.api.PerformanceDetailApiServiceImpl;
import com.culturespot.external.api.PerformancePeriodApiService;
import com.culturespot.external.api.PerformancePeriodApiServiceImpl;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.netty.channel.ChannelOption;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class ExternalApiConfig {

  @Value("${open-api.service-key}")
  private String openApiServiceKey;


  private WebClient webClient() {
    return WebClient.builder()
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE)
        .codecs(configurer -> {
          configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024);
        })
        .clientConnector(new ReactorClientHttpConnector(
            HttpClient.create()
                .responseTimeout(Duration.ofSeconds(60))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 15000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .compress(true)
        ))
        .build();
  }

  public RateLimiter rateLimiter() {
    RateLimiterConfig config = RateLimiterConfig.custom()
        .timeoutDuration(Duration.ofSeconds(1))
        .limitRefreshPeriod(Duration.ofSeconds(1))
        .limitForPeriod(10000)
        .build();
    return RateLimiter.of("performanceApiRateLimiter", config);
  }

  public XmlMapper xmlMapper() {
    JacksonXmlModule xmlModule = new JacksonXmlModule();
    xmlModule.setDefaultUseWrapper(false);

    XmlMapper xmlMapper = new XmlMapper(xmlModule);
    xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    xmlMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

    return xmlMapper;
  }

  @Bean
  public PerformancePeriodApiService performancePeriodApiService() {
    String encodedServiceKey = URLEncoder.encode(openApiServiceKey, StandardCharsets.UTF_8);
    return new PerformancePeriodApiServiceImpl(
        "https://apis.data.go.kr/B553457/nopenapi/rest/publicperformancedisplays/period",
        encodedServiceKey,
        webClient(),
        rateLimiter(),
        xmlMapper()
    );
  }

  @Bean
  public PerformanceDetailApiService performanceDetailApiService() {
    String encodedServiceKey = URLEncoder.encode(openApiServiceKey, StandardCharsets.UTF_8);
    return new PerformanceDetailApiServiceImpl(
        "https://apis.data.go.kr/B553457/nopenapi/rest/publicperformancedisplays/detail",
        encodedServiceKey,
        webClient(),
        rateLimiter(),
        xmlMapper()
    );
  }
}