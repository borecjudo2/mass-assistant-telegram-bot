package com.peka.massassistanttelegrambot.message.handler.impl;

import com.peka.massassistanttelegrambot.exception.TelegramException;
import com.peka.massassistanttelegrambot.message.BotMessagesUtils;
import com.peka.massassistanttelegrambot.message.handler.BotMessageHandler;
import com.peka.massassistanttelegrambot.model.MessageStep;
import com.peka.massassistanttelegrambot.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
public class CalculateAgeBotMessageHandler extends BotMessageHandler {

  @Override
  protected boolean isNeededMessageType(Update update) {
    return update.hasMessage();
  }

  @Override
  protected MessageStep getMessageStep() {
    return MessageStep.CALCULATE_AGE;
  }

  @Override
  protected MessageStep getNextMessageStep() {
    return MessageStep.CALCULATE_WEIGHT;
  }

  @Override
  protected User fillUserData(Update update, User user) throws TelegramApiException {
    int age = validateAge(update);

    user.setAge(age);

    DeleteMessage messageToDelete = DeleteMessage.builder()
        .chatId(update.getMessage().getChatId())
        .messageId(update.getMessage().getMessageId())
        .build();

    getBot().execute(messageToDelete);

    return user;
  }

  private int validateAge(Update update) {
    try {
      int age = Integer.parseInt(update.getMessage().getText());

      if (age <= 0 || age >= 150) {
        throw new Exception();
      }

      return age;
    } catch (Exception exception) {
      throw new TelegramException("Ошибка валидации возраста! Повторите еще раз!", update);
    }
  }

  @Override
  protected SendMessage createNextMessage(Update update, User user) {
    return SendMessage.builder()
        .chatId(update.getMessage().getChatId())
        .text(BotMessagesUtils.CALCULATE_WEIGHT_MESSAGE)
        .build();
  }
}
