package dev.itltcanz.app.handler.strategy.event.impl;

import dev.itltcanz.app.handler.strategy.event.doc.NotificationRepoStrategy;
import dev.itltcanz.app.model.entity.PushNotificationEntity;
import dev.itltcanz.app.model.enums.NotificationType;
import dev.itltcanz.app.repo.PushNotificationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PushNotificationHandler implements NotificationRepoStrategy<PushNotificationEntity> {

  private final PushNotificationRepo notificationRepo;

  @Override
  public NotificationType getTopic() {
    return NotificationType.PUSH;
  }

  @Override
  public PushNotificationRepo getRepo() {
    return notificationRepo;
  }

  @Override
  public Class<PushNotificationEntity> getEntityClass() {
    return PushNotificationEntity.class;
  }

}
