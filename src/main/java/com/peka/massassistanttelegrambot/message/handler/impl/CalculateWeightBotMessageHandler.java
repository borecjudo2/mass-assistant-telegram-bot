package com.peka.massassistanttelegrambot.message.handler.impl;

import com.peka.massassistanttelegrambot.exception.TelegramException;
import com.peka.massassistanttelegrambot.message.BotMessagesUtils;
import com.peka.massassistanttelegrambot.message.handler.BotMessageHandler;
import com.peka.massassistanttelegrambot.model.Emoji;
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
public class CalculateWeightBotMessageHandler extends BotMessageHandler {

  @Override
  protected boolean isNeededMessageType(Update update) {
    return update.hasMessage();
  }

  @Override
  protected MessageStep getMessageStep() {
    return MessageStep.CALCULATE_WEIGHT;
  }

  @Override
  protected MessageStep getNextMessageStep(User user) {
    return MessageStep.CALCULATE_HEIGHT;
  }

  @Override
  protected User fillUserData(Update update, User user) throws TelegramApiException {
    double weight = validateWeight(update);

    user.setWeight(weight);

    DeleteMessage messageToDelete = DeleteMessage.builder()
        .chatId(update.getMessage().getChatId())
        .messageId(update.getMessage().getMessageId())
        .build();

    getBot().execute(messageToDelete);

    return user;
  }

  private double validateWeight(Update update) {
    try {
      double weight = Double.parseDouble(update.getMessage().getText());

      if (weight <= 0 || weight >= 200) {
        throw new Exception();
      }

      return weight;
    } catch (Exception exception) {
      throw new TelegramException("Ошибка валидации веса! Повторите еще раз!", update, true);
    }
  }

  @Override
  protected SendMessage createNextMessage(Update update, User user) {
    return SendMessage.builder()
        .chatId(update.getMessage().getChatId())
        .text(String.format(
            BotMessagesUtils.CALCULATE_HEIGHT_MESSAGE,
            Emoji.RULER.getEmoji()
        ))
        .build();
  }
}
