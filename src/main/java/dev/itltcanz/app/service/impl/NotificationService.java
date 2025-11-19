package dev.itltcanz.app.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.itltcanz.app.handler.strategy.event.doc.NotificationRepoStrategy;
import dev.itltcanz.app.mapper.NotificationMapper;
import dev.itltcanz.app.model.entity.BaseNotificationEntity;
import dev.itltcanz.app.model.enums.NotificationType;
import dev.itltcanz.app.model.event.ExceptionEvent;
import dev.itltcanz.app.model.event.NotificationEvent;
import dev.itltcanz.app.service.doc.ValidationService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

  private final ObjectMapper objectMapper;
  private final EntityManager entityManager;
  private final PageRequest batchPageRequest;
  private final ValidationService validationService;
  private final NotificationMapper notificationMapper;
  private final ApplicationEventPublisher eventPublisher;
  private final Set<NotificationRepoStrategy<? extends BaseNotificationEntity>> strategySet;
  private final Map<NotificationType, NotificationRepoStrategy<? extends BaseNotificationEntity>> strategyMap = new EnumMap<>(
      NotificationType.class);

  @PostConstruct
  private void init() {
    strategySet.forEach(strategy -> strategyMap.put(strategy.getTopic(), strategy));
  }

  @Transactional
  public void handleEvent(ConsumerRecord<UUID, String> rec) {
    try {
      log.info("Уведомление обрабатывается. topic: {}", rec.topic());
      NotificationEvent event = notificationMapper.toEvent(rec);

      validationService.validate(rec);
      log.info("Уведомление успешно валидировано. topic: {}", rec.topic());

      var handler = strategyMap.get(event.getTopic());
      var notificationRepo = handler.getRepo();

      var json = objectMapper.writeValueAsString(event.getValue());

      if (notificationRepo.existsByKeyAndValue(rec.key(), json)) {
        throw new EntityExistsException("Уведомление не уникально");
      }
      log.info("Уведомление уникально. topic: {}", rec.topic());

      var notificationEntity = notificationMapper.toEntity(event, rec.key(), handler.getEntityClass());
      entityManager.persist(notificationEntity);
      log.info("Уведомление успешно сохранено. topic: {}", rec.topic());
    } catch (Exception e) {
      eventPublisher.publishEvent(new ExceptionEvent(this, rec.topic(), e));
    }
  }

  @Transactional
  public void handleNotifications() {
    Set<BaseNotificationEntity> notifications = getAll();
    for (var notification : notifications) {
      entityManager.merge(notification);
    }
  }

  private Set<BaseNotificationEntity> getAll() {
    var notifications = new HashSet<BaseNotificationEntity>();
    for (var strategy : strategySet) {
      var repo = strategy.getRepo();
      var page = repo.findByProcessedIsFalse(batchPageRequest).toSet();
      notifications.addAll(page);
    }
    return notifications;
  }
}
