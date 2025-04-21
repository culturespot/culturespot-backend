package com.culturespot.culturespotdomain.core.notification.repository;

import com.culturespot.culturespotdomain.core.notification.entity.Notification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {

  @PersistenceContext
  private final EntityManager entityManager;

  @Override
  public List<Notification> seekAllByUserIdAndGreaterThanIdWithPagination(Long userId, Long id,
      Pageable pageRequest) {
    return entityManager.createQuery(
            "SELECT n FROM Notification n WHERE n.userId = :userId AND n.id > :id ORDER BY n.createdAt DESC",
            Notification.class)
        .setParameter("userId", userId)
        .setParameter("id", id)
        .setFirstResult((int) pageRequest.getOffset())
        .setMaxResults(pageRequest.getPageSize())
        .getResultList();
  }
}