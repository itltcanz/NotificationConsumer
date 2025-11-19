package dev.itltcanz.app.model.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ExceptionEvent extends ApplicationEvent {

  private final String topic;
  private final Exception exception;

  public ExceptionEvent(Object source, String topic, Exception exception) {
    super(source);
    this.topic = topic;
    this.exception = exception;
  }
}
