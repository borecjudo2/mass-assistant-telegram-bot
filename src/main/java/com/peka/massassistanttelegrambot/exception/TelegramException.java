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
  private final boolean isCustomException;

  public TelegramException(Update update, Exception exception, boolean isCustomException) {
    super(exception.getLocalizedMessage());
    this.update = update;
    this.isCustomException = isCustomException;
  }

  public TelegramException(String message, Update update, boolean isCustomException) {
    super(message);
    this.update = update;
    this.isCustomException = isCustomException;
  }
}
