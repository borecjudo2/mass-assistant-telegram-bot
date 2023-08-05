package com.peka.massassistanttelegrambot.telegram;

import com.peka.massassistanttelegrambot.queue.BotQueue;
import com.peka.massassistanttelegrambot.telegram.properties.MassAssistantTelegramProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Component
@Slf4j
public class MassTelegramBot extends TelegramLongPollingBot {

  private final MassAssistantTelegramProperties telegramProperties;
  private final BotQueue queue;

  public MassTelegramBot(MassAssistantTelegramProperties telegramProperties, BotQueue queue) {
    super(telegramProperties.getApiKey());

    this.telegramProperties = telegramProperties;
    this.queue = queue;
  }

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
    queue.putUpdate(update);
  }

  @Override
  public String getBotUsername() {
    return telegramProperties.getName();
  }
}
