package com.peka.massassistanttelegrambot.scheduler.impl;

import com.peka.massassistanttelegrambot.repo.MongodbUserRepository;
import com.peka.massassistanttelegrambot.scheduler.DayResultSchedulerService;
import com.peka.massassistanttelegrambot.scheduler.DayResultTimerTask;
import com.peka.massassistanttelegrambot.service.CalculateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DayResultSchedulerServiceImpl implements DayResultSchedulerService {

  private final TelegramLongPollingBot bot;
  private final MongodbUserRepository userRepository;
  private final CalculateService calculateService;

  private final Map<Long, Timer> scheduledTasks = new ConcurrentHashMap<>();

  @Override
  public void schedule(long chatId, String timeZone) {
    Timer timer = new Timer();
    DayResultTimerTask timerTask = new DayResultTimerTask(bot, this, userRepository, calculateService, chatId);
    timer.schedule(timerTask, calculateDuration(timeZone));

    scheduledTasks.putIfAbsent(chatId, timer);

    log.info("Added day result schedule task for chatId=" + chatId);
  }

  @Override
  public boolean isExistTask(long chatId) {
    return scheduledTasks.get(chatId) != null;
  }


  @Override
  public void deleteSchedule(long chatId) {
    Timer timer = scheduledTasks.get(chatId);

    if (timer != null) {
      scheduledTasks.remove(chatId);
      log.info("Deleted day result schedule task for chatId=" + chatId);
    }
  }

  private long calculateDuration(String timeZone) {
    // Replace "yourZoneId" with the desired time zone identifier (e.g., "America/New_York")
    ZoneId zoneId = ZoneId.of(timeZone);

    // Get the current date and time for the specified time zone
    ZonedDateTime currentDateTime = ZonedDateTime.now(zoneId);

    // Calculate the duration to midnight
    ZonedDateTime midnight = currentDateTime.toLocalDate().plusDays(1).atTime(LocalTime.MIDNIGHT).atZone(zoneId);
    Duration durationToMidnight = Duration.between(currentDateTime, midnight);

    return durationToMidnight.toMillis();
  }
}
