package com.culturespot.culturespotbatch.job.performance;

import com.culturespot.culturespotdomain.core.performance.entity.Category;
import com.culturespot.culturespotdomain.core.performance.entity.Performance;
import com.culturespot.culturespotdomain.core.performance.entity.PerformanceInfo;
import com.culturespot.external.api.dto.PerformanceDetailResponse;
import com.culturespot.external.api.dto.PerformanceResponse;
import com.google.common.collect.ImmutableSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PerformanceMapper {

  Set<String> exhibitionValues = ImmutableSet.of("전시", "미술", "건축", "영상", "문학", "문화정책");

  @Mappings({
      @Mapping(target = "seq", source = "item.seq"),
      @Mapping(target = "title", source = "item.title"),
      @Mapping(target = "startDate", source = "item.startDate", qualifiedByName = "stringToLocalDate"),
      @Mapping(target = "endDate", source = "item.endDate", qualifiedByName = "stringToLocalDate"),
      @Mapping(target = "type", source = "item.realmName", qualifiedByName = "realmNameToType"),
      @Mapping(target = "category", source = "item.realmName", qualifiedByName = "realmNameToCategory"),
      @Mapping(target = "place", source = "detailItem.place"),
      @Mapping(target = "address", source = "detailItem.placeAddr"),
      @Mapping(target = "gpsX", source = "detailItem.gpsX", qualifiedByName = "stringToDouble"),
      @Mapping(target = "gpsY", source = "detailItem.gpsY", qualifiedByName = "stringToDouble"),
      @Mapping(target = "area", source = "detailItem.area"),
      @Mapping(target = "performanceInfo", source = "detailItem")
  })
  Performance toPerformance(PerformanceResponse.Item item,
      PerformanceDetailResponse.Item detailItem);

  @Mappings({
      @Mapping(target = "thumbnail", source = "imgUrl"),
      @Mapping(target = "imageUrl", source = "imgUrl"),
      @Mapping(target = "description", source = "contents1"),
      @Mapping(target = "url", source = "url"),
      @Mapping(target = "phone", source = "phone"),
      @Mapping(target = "price", source = "price")
  })
  PerformanceInfo toPerformanceInfo(PerformanceDetailResponse.Item detailItem);

  @Named("realmNameToCategory")
  default Category realmNameToCategory(String realmName) {
    return Category.fromName(realmName);
  }

  @Named("stringToLocalDate")
  default LocalDate stringToLocalDate(String date) {
    return LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
  }

  @Named("stringToDouble")
  default double stringToDouble(String value) {
    try {
      return Double.parseDouble(value);
    } catch (NumberFormatException e) {
      return 0.0;
    }
  }

  @Named("realmNameToType")
  default String realmNameToType(String realmName) {
    if (exhibitionValues.contains(realmName)) {
      return "EXHIBITION";
    }
    return "PERFORMANCE";
  }
}