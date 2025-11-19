package dev.itltcanz.app.handler.strategy.event.impl;

import dev.itltcanz.app.handler.strategy.event.doc.NotificationRepoStrategy;
import dev.itltcanz.app.model.entity.EmailNotificationEntity;
import dev.itltcanz.app.model.enums.NotificationType;
import dev.itltcanz.app.repo.EmailNotificationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailNotificationHandler implements NotificationRepoStrategy<EmailNotificationEntity> {

  private final EmailNotificationRepo notificationRepo;

  @Override
  public NotificationType getTopic() {
    return NotificationType.EMAIL;
  }

  @Override
  public EmailNotificationRepo getRepo() {
    return notificationRepo;
  }

  @Override
  public Class<EmailNotificationEntity> getEntityClass() {
    return EmailNotificationEntity.class;
  }

}
