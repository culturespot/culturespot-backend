package com.culturespot.culturespotbatch.job.performance;

import com.culturespot.culturespotdomain.core.performance.entity.Category;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDate;

public class PerformanceDto {

  private Long id;

  private String seq;

  private String type;

  @Enumerated(EnumType.STRING)
  private Category category;

  private String title;

  private LocalDate startDate;

  private LocalDate endDate;

  private String place;

  private String address;

  private double gpsX;

  private double gpsY;

  private String area;

  private String thumbnail;

  private String imageUrl;

  private String description;

  private String url;

  private String phone;

  private String price;
}
