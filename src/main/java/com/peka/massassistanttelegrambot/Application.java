package com.peka.massassistanttelegrambot;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;

@SpringBootApplication
@EnableScheduling
@EnableMongoRepositories
@Slf4j
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
    printCurrentIp();
  }

  @SneakyThrows
  private static void printCurrentIp() {
    InetAddress ip = InetAddress.getLocalHost();
    log.info("Current IP address : " + ip.getHostAddress());
  }
}
