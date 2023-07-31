package com.peka.massassistanttelegrambot.telegram.config;

import com.peka.massassistanttelegrambot.handler.TelegramBotUpdateHandler;
import com.peka.massassistanttelegrambot.telegram.properties.MassAssistantTelegramProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Configuration
@Slf4j
public class TelegramConfig {

  @Bean
  public TelegramLongPollingBot massAssistantTelegramBot(
      TelegramBotUpdateHandler telegramBotUpdateHandler,
      MassAssistantTelegramProperties telegramProperties
  ) {
    return new TelegramLongPollingBot(telegramProperties.getApiKey()) {
      @Override
      public void onUpdateReceived(Update update) {
        String chatId = "";
        String messageId = "";
        String username = "";

        if (update.hasMessage()) {
          chatId = String.valueOf(update.getMessage().getChatId());
          messageId = String.valueOf(update.getMessage().getMessageId());
          username = update.getMessage().getChat().getUserName();
        }

        if (update.hasCallbackQuery()) {
          chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
          messageId = String.valueOf(update.getCallbackQuery().getMessage().getMessageId());
          username = update.getCallbackQuery().getMessage().getChat().getUserName();
        }

        log.info(String.format("Received update. ChatId=%s, MessageId=%s, UserName=%s.", chatId, messageId, username));

        telegramBotUpdateHandler.handle(update);
      }

      @Override
      public String getBotUsername() {
        return telegramProperties.getName();
      }
    };
  }
}
