package com.culturespot.culturespotserviceapi.core.notification.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationResponse {

  private Long id;
  private boolean hasBeenRead;
  private String contents;
}
