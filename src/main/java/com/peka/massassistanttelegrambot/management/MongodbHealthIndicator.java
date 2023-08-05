package com.peka.massassistanttelegrambot.management;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MongodbHealthIndicator implements HealthIndicator {

  private final MongoTemplate mongoTemplate;

  @Override
  public Health health() {
    try {
      String dbName = mongoTemplate.getDb().getName();
      log.info(dbName + " Mongodb is up!");

      return Health.up().build();
    } catch (Exception exception) {
      log.error("Mongodb is down!", exception);
      return Health.down().build();
    }
  }
}
