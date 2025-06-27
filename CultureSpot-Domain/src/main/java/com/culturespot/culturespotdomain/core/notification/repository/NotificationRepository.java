package com.culturespot.culturespotdomain.core.notification.repository;

import com.culturespot.culturespotdomain.core.notification.entity.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long>,
    NotificationRepositoryCustom {

  List<Notification> findAllByUserIdAndHasBeenReadIsFalse(Long userId);
}
