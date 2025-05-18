package com.culturespot.culturespotserviceapi.core.notification.dto.response;

import java.util.List;
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

  private Long page;
  private Long size;
  private List<NotificationResponseItem> notifications;

  @Getter
  @Builder
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class NotificationResponseItem {

    private Long id;
    private boolean hasBeenRead;
    private Map<String, Object> contents;
  }
}
