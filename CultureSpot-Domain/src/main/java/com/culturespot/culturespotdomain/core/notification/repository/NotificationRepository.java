package com.culturespot.culturespotdomain.core.notification.repository;

import com.culturespot.culturespotdomain.core.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long>,
    NotificationRepositoryCustom {

}
