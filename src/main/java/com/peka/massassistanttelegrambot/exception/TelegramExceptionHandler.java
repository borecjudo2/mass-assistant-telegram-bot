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

    SendMessage messageIsBlank = SendMessage.builder()
        .chatId(exception.getUpdate().getMessage().getChatId())
        .text("Упс! Ты меня сломал! " + exception.getLocalizedMessage().split(":")[1])
        .build();
    bot.execute(messageIsBlank);
  }
}
