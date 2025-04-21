package com.culturespot.culturespotdomain.core.notification.service;

import com.culturespot.culturespotdomain.core.notification.entity.Notification;
import com.culturespot.culturespotdomain.core.notification.repository.NotificationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationService {

  private final NotificationRepository repository;

  public List<Notification> getNotificationsBy(Long UserId, Long lastId, Long page, Long size) {

    return null;
  }
}
