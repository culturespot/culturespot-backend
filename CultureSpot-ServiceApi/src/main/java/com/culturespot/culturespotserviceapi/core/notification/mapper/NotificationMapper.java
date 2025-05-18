package com.culturespot.culturespotserviceapi.core.notification.mapper;

import com.culturespot.culturespotdomain.core.notification.entity.Notification;
import com.culturespot.culturespotserviceapi.core.notification.dto.response.NotificationResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

  @Mapping(source = "contents", target = "contents", qualifiedByName = "stringToMap")
  NotificationResponse.NotificationResponseItem toNotificationResponse(Notification notification);

  List<NotificationResponse.NotificationResponseItem> toNotificationResponses(
      List<Notification> notifications);

  @Named("stringToMap")
  default Map<String, Object> stringToMap(String contents) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readValue(contents, new TypeReference<Map<String, Object>>() {
      });
    } catch (Exception e) {
      throw new RuntimeException("Failed to convert JSON string to Map", e);
    }
  }
}
