package com.peka.massassistanttelegrambot.handler;

import com.peka.massassistanttelegrambot.queue.BotQueue;
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

  private final BotQueue queue;

  public void handle(Update update) {
    queue.putUpdate(update);
  }
}
