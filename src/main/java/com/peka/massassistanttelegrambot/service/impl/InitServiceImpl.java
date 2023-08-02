package com.peka.massassistanttelegrambot.service.impl;

import com.peka.massassistanttelegrambot.service.InitService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Service
public class InitServiceImpl implements InitService {

  private final String url;

  public InitServiceImpl(@Value("${google.app.url}") String url) {
    this.url = url;
  }

  @PostConstruct
  @Override
  public void init() {
    new RestTemplate().getForEntity(url, String.class);
  }
}
