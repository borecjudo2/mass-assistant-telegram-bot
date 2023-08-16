package com.peka.massassistanttelegrambot.message.handler.impl;

import com.peka.massassistanttelegrambot.exception.TelegramException;
import com.peka.massassistanttelegrambot.message.handler.BotMessageHandler;
import com.peka.massassistanttelegrambot.model.CalculateType;
import com.peka.massassistanttelegrambot.model.Emoji;
import com.peka.massassistanttelegrambot.model.MessageStep;
import com.peka.massassistanttelegrambot.model.User;
import com.peka.massassistanttelegrambot.utils.BotMessagesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.Collections;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class CalculateFatsBotMessageHandler extends BotMessageHandler {

  @Override
  protected boolean isNeededMessageType(Update update) {
    return update.hasMessage();
  }

  @Override
  protected MessageStep getMessageStep() {
    return MessageStep.CALCULATE_FATS;
  }

  @Override
  protected MessageStep getNextMessageStep(User user) {
    return MessageStep.CALCULATE_TYPE;
  }

  @Override
  protected User fillUserData(Update update, User user) {
    int fats = validateFats(update);
    user.setFatPercentage(fats);

    return user;
  }

  private int validateFats(Update update) {
    try {
      int fats = Integer.parseInt(update.getMessage().getText());

      if (fats <= 0 || fats >= 100) {
        throw new Exception();
      }

      return fats;
    } catch (Exception exception) {
      throw new TelegramException(BotMessagesUtils.ERROR_VALIDATION_FATS_PERCENTAGE, update, true);
    }
  }

  @Override
  protected SendMessage createNextMessage(Update update, User user) {
    return SendMessage.builder()
        .chatId(update.getMessage().getChatId())
        .text(String.format(
            BotMessagesUtils.CALCULATE_TYPE_MESSAGE,
            Emoji.TROPHY.getEmoji()
        ))
        .replyMarkup(createInlineKeyboardMarkup())
        .build();
  }

  private InlineKeyboardMarkup createInlineKeyboardMarkup() {
    InlineKeyboardButton deficitButton = InlineKeyboardButton.builder()
        .text(String.valueOf(CalculateType.DEFICIT.getData()))
        .callbackData(CalculateType.DEFICIT.toString())
        .build();

    InlineKeyboardButton normalButton = InlineKeyboardButton.builder()
        .text(String.valueOf(CalculateType.NORMAL.getData()))
        .callbackData(CalculateType.DEFICIT.toString())
        .build();

    InlineKeyboardButton profitButton = InlineKeyboardButton.builder()
        .text(String.valueOf(CalculateType.PROFIT.getData()))
        .callbackData(CalculateType.PROFIT.toString())
        .build();

    return InlineKeyboardMarkup.builder()
        .keyboard(Arrays.asList(
            Collections.singletonList(deficitButton),
            Collections.singletonList(normalButton),
            Collections.singletonList(profitButton))
        )
        .build();
  }
}
