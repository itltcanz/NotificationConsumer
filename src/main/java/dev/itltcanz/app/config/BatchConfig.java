package dev.itltcanz.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Configuration
public class BatchConfig {

  @Value("${inbox.batch-size}")
  private int batchSize;

  @Value("${inbox.batch-sort-field}")
  private String batchSortField;

  @Bean
  public PageRequest batchPageRequest() {
    Sort sort = Sort.by(Direction.ASC, batchSortField);
    return PageRequest.of(0, batchSize, sort);
  }
}
