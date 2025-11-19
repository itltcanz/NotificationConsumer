package dev.itltcanz.app.kafka.consumer;

import dev.itltcanz.app.service.impl.NotificationService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationConsumer {

  private final NotificationService notificationService;

  @KafkaListener(
      topics = "${spring.kafka.topic}"
  )
  public void consume(ConsumerRecord<UUID, String> rec) {
    log.info("Уведомление принято. topic: {}", rec.topic());
    notificationService.handleEvent(rec);
  }
}
