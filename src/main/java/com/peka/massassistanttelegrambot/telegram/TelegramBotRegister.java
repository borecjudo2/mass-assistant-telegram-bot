package com.peka.massassistanttelegrambot.telegram;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class TelegramBotRegister {

  private final TelegramLongPollingBot bot;

  @PostConstruct
  public void init() {
    try {
      new TelegramBotsApi(DefaultBotSession.class).registerBot(bot);
    } catch (TelegramApiException exception) {
      log.error("Telegram bot initialisation was failed!", exception);
    }
  }
}
