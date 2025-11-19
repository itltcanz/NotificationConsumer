package dev.itltcanz.app.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.itltcanz.app.exception.ParsingException;
import dev.itltcanz.app.model.entity.BaseNotificationEntity;
import dev.itltcanz.app.model.enums.NotificationType;
import dev.itltcanz.app.model.event.NotificationEvent;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.mapping.MappingException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationMapper {

  private final ObjectMapper objectMapper;

  public NotificationEvent toEvent(ConsumerRecord<UUID, String> rec) {
    try {
      Map<String, Object> raw = objectMapper.readValue(rec.value(), new TypeReference<>() {});
      Map<String, Object> value = objectMapper.convertValue(raw.get("value"), new TypeReference<>() {});
      NotificationType topic = NotificationType.fromString(raw.get("topic").toString());

      return new NotificationEvent(topic, value);
    } catch (Exception e) {
      throw new ParsingException("Ошибка парсинга сообщения " + rec.topic(), e);
    }
  }

  public <T extends BaseNotificationEntity> T toEntity(NotificationEvent event, UUID key, Class<T> clazz) {
    try {
      T entity = clazz.getDeclaredConstructor().newInstance();

      entity.setTopic(event.getTopic());
      entity.setKey(key);
      entity.setValue(event.getValue());

      return entity;
    } catch (Exception e) {
      throw new MappingException("Ошибка маппинга объекта " + clazz.getName(), e);
    }
  }
}
