package com.culturespot.culturespotdomain.core.performance.entity;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

// No need setter method, refer to https://sedangdang.tistory.com/307
@ToString
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceInfo {

  private String thumbnail; //

  private String imageUrl; //

  private String description; //

  private String url; //

  private String phone; //

  private String price; //
}
