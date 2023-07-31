package com.peka.massassistanttelegrambot.message.handler.impl;

import com.peka.massassistanttelegrambot.exception.TelegramException;
import com.peka.massassistanttelegrambot.message.BotMessagesUtils;
import com.peka.massassistanttelegrambot.message.handler.BotMessageHandler;
import com.peka.massassistanttelegrambot.model.ActivityRate;
import com.peka.massassistanttelegrambot.model.MessageStep;
import com.peka.massassistanttelegrambot.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class CalculateHeightBotMessageHandler extends BotMessageHandler {

  @Override
  protected boolean isNeededMessageType(Update update) {
    return update.hasMessage();
  }

  @Override
  protected MessageStep getMessageStep() {
    return MessageStep.CALCULATE_HEIGHT;
  }

  @Override
  protected MessageStep getNextMessageStep() {
    return MessageStep.CALCULATE_ACTIVITY;
  }

  @Override
  protected User fillUserData(Update update, User user) throws TelegramApiException {
    double height = validateHeight(update);

    user.setHeight(height);

    DeleteMessage messageToDelete = DeleteMessage.builder()
        .chatId(update.getMessage().getChatId())
        .messageId(update.getMessage().getMessageId())
        .build();

    getBot().execute(messageToDelete);

    return user;
  }

  private double validateHeight(Update update) {
    try {
      double height = Double.parseDouble(update.getMessage().getText());

      if (height <= 100 || height >= 300) {
        throw new Exception();
      }
      return height;
    } catch (Exception exception) {
      throw new TelegramException("Ошибка валидации роста! Повторите еще раз!", update);
    }
  }

  @Override
  protected SendMessage createNextMessage(Update update, User user) {
    return SendMessage.builder()
        .chatId(update.getMessage().getChatId())
        .text(BotMessagesUtils.CALCULATE_ACTIVITY_MESSAGE)
        .replyMarkup(createInlineKeyboardMarkup())
        .build();
  }

  private InlineKeyboardMarkup createInlineKeyboardMarkup() {
    InlineKeyboardButton noneButton = InlineKeyboardButton.builder()
        .text(String.valueOf(ActivityRate.NONE.getRate()))
        .callbackData(ActivityRate.NONE.toString())
        .build();

    InlineKeyboardButton lowerButton = InlineKeyboardButton.builder()
        .text(String.valueOf(ActivityRate.LOWER.getRate()))
        .callbackData(ActivityRate.LOWER.toString())
        .build();

    InlineKeyboardButton midLowerButton = InlineKeyboardButton.builder()
        .text(String.valueOf(ActivityRate.MID_LOWER.getRate()))
        .callbackData(ActivityRate.MID_LOWER.toString())
        .build();

    InlineKeyboardButton midUpperButton = InlineKeyboardButton.builder()
        .text(String.valueOf(ActivityRate.MID_UPPER.getRate()))
        .callbackData(ActivityRate.MID_UPPER.toString())
        .build();

    InlineKeyboardButton highLowerButton = InlineKeyboardButton.builder()
        .text(String.valueOf(ActivityRate.HIGH_LOWER.getRate()))
        .callbackData(ActivityRate.HIGH_LOWER.toString())
        .build();

    InlineKeyboardButton highUpperButton = InlineKeyboardButton.builder()
        .text(String.valueOf(ActivityRate.HIGH_UPPER.getRate()))
        .callbackData(ActivityRate.HIGH_UPPER.toString())
        .build();

    InlineKeyboardButton hyperButton = InlineKeyboardButton.builder()
        .text(String.valueOf(ActivityRate.HYPER.getRate()))
        .callbackData(ActivityRate.HYPER.toString())
        .build();

    return InlineKeyboardMarkup.builder()
        .keyboard(Arrays.asList(
            Arrays.asList(noneButton, lowerButton, midLowerButton, midUpperButton),
            Arrays.asList(highLowerButton, highUpperButton, hyperButton)))
        .build();
  }
}
