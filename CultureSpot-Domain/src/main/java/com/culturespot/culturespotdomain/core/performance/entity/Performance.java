package com.culturespot.culturespotdomain.core.performance.entity;

import com.culturespot.culturespotdomain.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(callSuper = true)
@Builder(toBuilder = true, access = AccessLevel.PUBLIC)
@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Performance extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column
  private String seq;

  @Column
  private String type;

  @Enumerated(EnumType.STRING)
  @Column
  private Category category;

  @Column
  private String title;

  @Column
  private LocalDate startDate;

  @Column
  private LocalDate endDate;

  @Column
  private String place;

  @Column
  private String address;//

  @Column
  private double gpsX;

  @Column
  private double gpsY;

  @Column
  private String area;

  @Convert(converter = PerformanceInfoConverter.class)
  @Column(columnDefinition = "TEXT")
  private PerformanceInfo performanceInfo;

  public void updateWith(Performance that) {
    this.seq = that.seq;
    this.type = that.type;
    this.category = that.category;
    this.title = that.title;
    this.startDate = that.startDate;
    this.endDate = that.endDate;
    this.place = that.place;
    this.address = that.address;
    this.gpsX = that.gpsX;
    this.gpsY = that.gpsY;
    this.area = that.area;
    this.performanceInfo = that.performanceInfo;
  }
}
