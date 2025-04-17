package com.culturespot.culturespotbatch.job.performance;

import com.culturespot.culturespotdomain.core.performance.entity.Category;
import com.culturespot.culturespotdomain.core.performance.entity.Event;
import com.culturespot.culturespotdomain.core.performance.entity.Performance;
import com.culturespot.culturespotdomain.core.performance.entity.PerformanceInfo;
import com.culturespot.external.api.dto.PerformanceDetailResponse;
import com.culturespot.external.api.dto.PerformanceResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PerformanceMapper {

  @Mappings({
      @Mapping(target = "seq", source = "item.seq"),
      @Mapping(target = "title", source = "item.title"),
      @Mapping(target = "startDate", source = "item.startDate", qualifiedByName = "stringToLocalDate"),
      @Mapping(target = "endDate", source = "item.endDate", qualifiedByName = "stringToLocalDate"),
      @Mapping(target = "type", expression = "java(realmNameToType(item.getServiceName(), item.getRealmName()))"),
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
  default Event realmNameToType(String serviceName, String realmName) {
    Category category = Category.fromName(realmName);
    if (Category.EXHIBITION == category || Category.MUSIC_CONCERT == category) {
      return Event.PERFORMANCE_EXHIBITION;
    }

    if (Category.EVENT_FESTIVAL == category) {
      return Event.EVENT_FESTIVAL;
    }

    if (Category.EDU_EXPERIENCE == category) {
      return Event.EDU_EXPERIENCE;
    }

    return Event.fromName(serviceName);
  }
}