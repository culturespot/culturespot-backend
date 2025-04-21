package com.culturespot.culturespotserviceapi.core.notification.controller;

import com.culturespot.culturespotdomain.core.notification.entity.Notification;
import com.culturespot.culturespotdomain.core.notification.service.NotificationService;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.core.auth.annotation.Auth;
import com.culturespot.culturespotserviceapi.core.auth.annotation.MemberOnly;
import com.culturespot.culturespotserviceapi.core.notification.dto.response.NotificationResponse;
import com.culturespot.culturespotserviceapi.core.notification.mapper.NotificationMapper;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class NotificationController {

  private final NotificationService service;
  private final NotificationMapper mapper;

  @MemberOnly
  @GetMapping(value = "/api/users/notifications")
  public List<NotificationResponse> getNotifications(
      @RequestParam(required = false, defaultValue = "1") Long page,
      @RequestParam(required = false, defaultValue = "10") Long size,
      @RequestParam(required = false, defaultValue = "0") Long lastId,
      @Auth User user
  ) {

    log.info(
        "NotificationController get request to get notifications. `page`: {}, `size`: {}, `lastId`: {}",
        page, size, lastId);

    List<Notification> notifications = service.getNotificationsBy(user.getId(), lastId, page, size);
    log.info("A number of notifications is {}.", notifications.size());

    return mapper.toNotifincationResponses(notifications);
  }

  @MemberOnly
  @PutMapping(value = "/api/users/notifications/{notificationId}/read")
  public NotificationResponse readNotification(@PathVariable Long notificationId, @Auth User user) {
    log.info("NotificationController get request to read notification. `notificationId`: {}",
        notificationId);
    Optional<Notification> maybeUpdatedNotification = service.read(user.getId(), notificationId);
    if (maybeUpdatedNotification.isEmpty()) {
      throw new NoSuchElementException("Not found notification");
    }

    return mapper.toNotifincationResponse(maybeUpdatedNotification.get());
  }

  @MemberOnly
  @PutMapping(value = "/api/users/notifications/read-all")
  public List<NotificationResponse> readWholeNotifications(@Auth User user) {
    log.info("NotificationController get request to read whole notifications.");

    List<Notification> notifications = service.readWholeNotifications(user.getId());
    return mapper.toNotifincationResponses(notifications);
  }
}
