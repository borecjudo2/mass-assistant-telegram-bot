package com.peka.massassistanttelegrambot.controller;

import com.peka.massassistanttelegrambot.exception.TelegramExceptionHandler;
import com.peka.massassistanttelegrambot.exception.TelegramException;
import com.peka.massassistanttelegrambot.queue.BotQueue;
import com.peka.massassistanttelegrambot.service.BotService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@EnableAsync
@Component
@RequiredArgsConstructor
public class TelegramBotUpdateController {

  private final BotQueue queue;
  private final BotService service;
  private final TelegramExceptionHandler exceptionHandler;

  @Async
  @Scheduled(fixedRate = 1000)
  public void processUpdate() {
    try {
      Update update = queue.takeUpdate();
      service.processUpdate(update);
    } catch (TelegramException exception) {
      exceptionHandler.handle(exception);
    }
  }
}
