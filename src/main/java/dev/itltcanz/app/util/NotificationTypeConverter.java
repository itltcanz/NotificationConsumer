package dev.itltcanz.app.util;

import dev.itltcanz.app.model.enums.NotificationType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class NotificationTypeConverter implements AttributeConverter<NotificationType, String> {

  @Override
  public String convertToDatabaseColumn(NotificationType attribute) {
    return attribute != null ? attribute.getTopic() : null;
  }

  @Override
  public NotificationType convertToEntityAttribute(String dbData) {
    if (dbData == null) return null;
    return NotificationType.fromString(dbData);
  }
}
