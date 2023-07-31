package com.peka.massassistanttelegrambot.exception;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */

@Getter
public class TelegramException extends RuntimeException {

  private final Update update;

  public TelegramException(Update update, Exception exception) {
    super(exception);
    this.update = update;
  }

  public TelegramException(String message, Update update) {
    super(message);
    this.update = update;
  }
}
