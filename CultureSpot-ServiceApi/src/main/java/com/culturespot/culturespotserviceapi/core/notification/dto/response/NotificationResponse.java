package com.culturespot.culturespotserviceapi.core.notification.dto.response;

import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationResponse {

  private Long id;
  private boolean hasBeenRead;
  private Map<String, Object> contents;
}
