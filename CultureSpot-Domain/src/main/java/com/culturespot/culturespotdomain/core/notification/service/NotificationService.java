package com.culturespot.culturespotdomain.core.notification.service;

import com.culturespot.culturespotdomain.core.global.exception.DomainException;
import com.culturespot.culturespotdomain.core.global.exception.DomainExceptionCode;
import com.culturespot.culturespotdomain.core.notification.entity.Notification;
import com.culturespot.culturespotdomain.core.notification.repository.NotificationRepository;
import java.util.List;
import java.util.Objects;
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
  public Optional<Notification> read(Long userId, Long notificationId) {
    Optional<Notification> maybeNotification = repository.findById(notificationId);

    if (maybeNotification.isEmpty()) {
      log.warn("Notification does not exist. `id`: {}", notificationId);
      return Optional.empty();
    }

    Notification notification = maybeNotification.get();

    if (!Objects.equals(notification.getUserId(), userId)) {
      log.warn("User do not have this notification. `userId`: {}, `notificationId`: {}", userId,
          notificationId);
      throw new DomainException(DomainExceptionCode.NOTIFICATION_EDIT_PERMISSION_DENIED);
    }

    Notification shouldBeUpdatedNotification = notification.toBuilder().hasBeenRead(true).build();
    return Optional.of(repository.save(shouldBeUpdatedNotification));
  }
}
