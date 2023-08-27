package com.peka.massassistanttelegrambot.config;

import net.iakovlev.timeshape.TimeZoneEngine;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Configuration
public class LocationConfig {

  @Bean
  @ConditionalOnProperty(name = "service.location.enabled", havingValue = "true")
  public TimeZoneEngine timeZoneEngine() {
    return TimeZoneEngine.initialize(35, -10, 75, 180, false);
  }
}
