package com.peka.massassistanttelegrambot.scheduler;

import com.peka.massassistanttelegrambot.model.User;
import com.peka.massassistanttelegrambot.repo.MongodbUserRepository;
import com.peka.massassistanttelegrambot.service.CalculateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;
import java.util.TimerTask;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */

@Slf4j
@RequiredArgsConstructor
public class DayResultTimerTask extends TimerTask {

  private final TelegramLongPollingBot bot;
  private final DayResultSchedulerService schedulerService;
  private final MongodbUserRepository userRepository;
  private final CalculateService calculateService;

  private final long chatId;

  @Override
  public void run() {
    if (!schedulerService.isExistTask(chatId)) {
      log.info("Scheduled day result task was removed chatId=" + chatId);
      return;
    }

    User existingUser = userRepository.findById(chatId)
        .orElseThrow(() -> new RuntimeException("User not found with chatId=" + chatId));

    SendMessage messageToSend = SendMessage.builder()
        .chatId(chatId)
        .text(calculateService.calculateDayResult(existingUser))
        .build();

    try {
      bot.execute(messageToSend);

      existingUser.setAteFoodsByDay(Collections.emptyList());
      userRepository.save(existingUser);

      schedulerService.schedule(chatId, existingUser.getTimeZone());
    } catch (TelegramApiException exception) {
      log.error("Error during sending scheduled day result message to chatId=" + chatId, exception);
    }
  }
}
