package com.peka.massassistanttelegrambot.local.spring;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Arrays;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
public class MongodbLocalApplicationContextInitializer implements ApplicationContextInitializer {

  private static final String LOCAL_ENV = "local";

  private final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    Arrays.stream(applicationContext.getEnvironment().getActiveProfiles())
        .filter(env -> env.equals(LOCAL_ENV))
        .findFirst()
        .ifPresent(env -> {
          mongoDBContainer.start();

          String host = mongoDBContainer.getHost();
          Integer port = mongoDBContainer.getFirstMappedPort();
          String connectionString = String.format("mongodb://%s:%d", host, port);

          System.setProperty("spring.data.mongodb.uri", connectionString);
          System.setProperty("spring.data.mongodb.database", "test");
        });
  }
}
