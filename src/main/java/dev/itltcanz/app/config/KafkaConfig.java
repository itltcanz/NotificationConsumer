package dev.itltcanz.app.config;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.DeserializationException;
import org.springframework.util.backoff.FixedBackOff;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

  @Value("${spring.kafka.settings.retry-pause}")
  private int retryPause;

  @Value("${spring.kafka.settings.retry-number}")
  private int retryNumber;

  @Bean
  public DefaultErrorHandler errorHandler() {
    var backOff = new FixedBackOff(retryPause, retryNumber);
    var errorHandler = new DefaultErrorHandler(backOff);
    errorHandler.addNotRetryableExceptions(DeserializationException.class);
    return errorHandler;
  }

  @Bean
  public ConsumerFactory<UUID, String> consumerFactory(KafkaProperties properties) {
    Map<String, Object> props = new HashMap<>(properties.buildConsumerProperties());
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        org.apache.kafka.common.serialization.UUIDDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        org.apache.kafka.common.serialization.StringDeserializer.class);
    return new DefaultKafkaConsumerFactory<>(props);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<UUID, String> kafkaListenerContainerFactory(
      ConsumerFactory<UUID, String> consumerFactory,
      DefaultErrorHandler errorHandler
  ) {
    var factory = new ConcurrentKafkaListenerContainerFactory<UUID, String>();
    factory.setConsumerFactory(consumerFactory);
    factory.setCommonErrorHandler(errorHandler);
    return factory;
  }
}
