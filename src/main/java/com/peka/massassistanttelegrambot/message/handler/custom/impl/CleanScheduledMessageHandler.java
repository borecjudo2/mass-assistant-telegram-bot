package com.peka.massassistanttelegrambot.message.handler.custom.impl;

import com.peka.massassistanttelegrambot.exception.TelegramException;
import com.peka.massassistanttelegrambot.message.handler.custom.BotCustomMessageHandler;
import com.peka.massassistanttelegrambot.model.CallbackMessages;
import com.peka.massassistanttelegrambot.model.User;
import com.peka.massassistanttelegrambot.repo.MongodbUserRepository;
import com.peka.massassistanttelegrambot.utils.BotMessagesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class CleanScheduledMessageHandler implements BotCustomMessageHandler {

  private final MongodbUserRepository userRepository;
  private final TelegramLongPollingBot bot;

  @Override
  public boolean isMyMessage(Update update, User user) {
    return update.hasCallbackQuery()
           && update.getCallbackQuery().getData().contains(CallbackMessages.CLEAR_FOOD_SCHEDULED.toString());
  }

  @Override
  public User handleMessage(Update update, User user) {
    try {
      if (user == null) {
        throw new TelegramException(BotMessagesUtils.USER_NOT_LOGIN, update, true);
      }

      User existingUser = userRepository.findById(user.getId()).orElseThrow();
      existingUser.getAteFoodsByDay().clear();

      sendUpdatedMessage(update);

      return existingUser;
    } catch (TelegramApiException exception) {
      throw new TelegramException(update, exception, false);
    }
  }

  protected void sendUpdatedMessage(Update update) throws TelegramApiException {
    DeleteMessage deleteMessage = DeleteMessage.builder()
        .chatId(update.getCallbackQuery().getMessage().getChatId())
        .messageId(update.getCallbackQuery().getMessage().getMessageId())
        .build();

    bot.execute(deleteMessage);

    SendMessage sendMessage = SendMessage.builder()
        .chatId(update.getCallbackQuery().getMessage().getChatId())
        .text(BotMessagesUtils.CLEANED_FOOD_MESSAGE)
        .build();

    bot.execute(sendMessage);
  }
}
