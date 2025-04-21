package com.culturespot.culturespotserviceapi.core.notification.controller;

import com.culturespot.culturespotdomain.core.notification.entity.Notification;
import com.culturespot.culturespotdomain.core.notification.service.NotificationService;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.core.auth.annotation.Auth;
import com.culturespot.culturespotserviceapi.core.auth.annotation.MemberOnly;
import com.culturespot.culturespotserviceapi.core.notification.dto.response.NotificationResponse;
import com.culturespot.culturespotserviceapi.core.notification.mapper.NotificationMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NotificationController {

  private final NotificationService service;
  private final NotificationMapper mapper;

  @MemberOnly
  @GetMapping
  List<NotificationResponse> getNotifications(
      @RequestParam(required = false, defaultValue = "1") Long page,
      @RequestParam(required = false, defaultValue = "10") Long size,
      @RequestParam(required = false, defaultValue = "0") Long lastId,
      @Auth User user
  ) {

    List<Notification> notifications = service.getNotificationsBy(user.getId(), lastId, page, size);
    return mapper.toNotifincationResponses(notifications);
  }
}
