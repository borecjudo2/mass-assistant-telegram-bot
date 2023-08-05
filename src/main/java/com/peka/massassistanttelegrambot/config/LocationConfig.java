package com.peka.massassistanttelegrambot.config;

import net.iakovlev.timeshape.TimeZoneEngine;
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
  public TimeZoneEngine timeZoneEngine() {
    return TimeZoneEngine.initialize();
  }
}
