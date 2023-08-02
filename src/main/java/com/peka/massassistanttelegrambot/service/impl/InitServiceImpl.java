package com.peka.massassistanttelegrambot.service.impl;

import com.peka.massassistanttelegrambot.service.InitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Service
@Slf4j
public class InitServiceImpl implements InitService {

  private final String url;

  public InitServiceImpl(@Value("${google.app.url}") String url) {
    this.url = url;
  }

  @Async
  @Scheduled(initialDelay = 10000, fixedDelay = 3600000)
  @Override
  public void asyncGoogleAppCall() {
    ResponseEntity<String> forEntity = new RestTemplate().getForEntity(url, String.class);
    log.info(url + forEntity.getStatusCode().value());
  }
}
