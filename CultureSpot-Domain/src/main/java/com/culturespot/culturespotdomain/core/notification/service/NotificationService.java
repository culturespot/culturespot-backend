package com.culturespot.culturespotdomain.core.notification.service;

import com.culturespot.culturespotdomain.core.notification.entity.Notification;
import com.culturespot.culturespotdomain.core.notification.repository.NotificationRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

  private final NotificationRepository repository;

  public List<Notification> getNotificationsBy(Long userId, Long lastId, Long page, Long size) {
    List<Notification> candidates = repository.seekAllByUserIdAndGreaterThanIdWithPagination(
        userId, lastId,
        PageRequest.of(page.intValue(), size.intValue()));

    return candidates;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public Optional<Notification> read(Long notificationId) {
    Optional<Notification> maybeNotification = repository.findById(notificationId);

    if (maybeNotification.isEmpty()) {
      log.warn("Notification does not exist. `id`: {}", notificationId);
      return Optional.empty();
    }

    Notification notification = maybeNotification.get();
    Notification shouldBeUpdatedNotification = notification.toBuilder().hasBeenRead(true).build();
    return Optional.of(repository.save(shouldBeUpdatedNotification));
  }
}
