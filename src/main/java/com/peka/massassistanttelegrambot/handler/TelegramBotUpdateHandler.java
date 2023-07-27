package com.peka.massassistanttelegrambot.handler;

import com.peka.massassistanttelegrambot.controller.TelegramBotUpdateController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
public class TelegramBotUpdateHandler {

  private final TelegramBotUpdateController controller;

  public void handle(Update update) {
    System.out.println(update);
  }
}
