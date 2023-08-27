package com.peka.massassistanttelegrambot.scheduler;

import com.peka.massassistanttelegrambot.model.User;
import com.peka.massassistanttelegrambot.repo.MongodbUserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Service
@ConditionalOnProperty(name = "service.location.enabled", havingValue = "true")
@Slf4j
@RequiredArgsConstructor
public class StartupTaskLoader {

  private final MongodbUserRepository userRepository;
  private final DayResultSchedulerService schedulerService;

  @PostConstruct
  public void init() {
    Executors.newSingleThreadExecutor().execute(() -> {
      log.info("Started task loader");

      List<User> users = userRepository.findAllByIsResultTaskEnabled(true);
      users.forEach(user -> schedulerService.schedule(user.getId(), user.getTimeZone()));

      log.info("Finished task loader");
    });
  }
}
