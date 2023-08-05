package com.peka.massassistanttelegrambot.controller;

import com.peka.massassistanttelegrambot.exception.TelegramException;
import com.peka.massassistanttelegrambot.exception.TelegramExceptionHandler;
import com.peka.massassistanttelegrambot.queue.BotQueue;
import com.peka.massassistanttelegrambot.service.BotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class TelegramBotUpdateController {

  private final BotQueue queue;
  private final BotService service;
  private final TelegramExceptionHandler exceptionHandler;

  @Async("threadPoolTaskExecutor")
  @Scheduled(fixedRate = 100)
  public void processUpdate() {
    try {
      log.info("Just started processUpdate new iteration");

      Update update = queue.takeUpdate();
      service.processUpdate(update);
    } catch (TelegramException exception) {
      exceptionHandler.handle(exception);
    } catch (Exception exception) {
      log.error(exception.getLocalizedMessage(), exception);
    }
  }
}
