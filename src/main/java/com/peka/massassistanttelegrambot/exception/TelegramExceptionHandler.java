package com.peka.massassistanttelegrambot.exception;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * Rest exceptions handler.
 *
 * @author Vladislav Karpeka
 * @since 1.0.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class TelegramExceptionHandler {

  private final TelegramLongPollingBot bot;

  @SneakyThrows
  public void handle(TelegramException exception) {
    log.error(exception.getLocalizedMessage(), exception);

    long chatId = exception.getUpdate().hasMessage() ?
        exception.getUpdate().getMessage().getChatId() :
        exception.getUpdate().getCallbackQuery().getMessage().getChatId();

    StringBuilder stringBuilder = new StringBuilder("Упс! Ты меня сломал!\n");
    if (exception.isCustomException()) {
      stringBuilder.append(exception.getLocalizedMessage());
    }

    SendMessage messageIsBlank = SendMessage.builder()
        .chatId(chatId)
        .text(stringBuilder.toString())
        .build();
    bot.execute(messageIsBlank);
  }
}
