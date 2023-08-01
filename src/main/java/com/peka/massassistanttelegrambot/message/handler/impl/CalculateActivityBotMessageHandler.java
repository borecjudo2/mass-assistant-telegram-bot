package com.peka.massassistanttelegrambot.message.handler.impl;

import com.peka.massassistanttelegrambot.exception.TelegramException;
import com.peka.massassistanttelegrambot.message.BotMessagesUtils;
import com.peka.massassistanttelegrambot.message.handler.BotMessageHandler;
import com.peka.massassistanttelegrambot.model.ActivityRate;
import com.peka.massassistanttelegrambot.model.CalculateType;
import com.peka.massassistanttelegrambot.model.Emoji;
import com.peka.massassistanttelegrambot.model.MessageStep;
import com.peka.massassistanttelegrambot.model.User;
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
public class CalculateActivityBotMessageHandler extends BotMessageHandler {

  @Override
  protected boolean isNeededMessageType(Update update) {
    return update.hasCallbackQuery();
  }

  @Override
  protected MessageStep getMessageStep() {
    return MessageStep.CALCULATE_ACTIVITY;
  }

  @Override
  protected MessageStep getNextMessageStep() {
    return MessageStep.CALCULATE_TYPE;
  }

  @Override
  protected User fillUserData(Update update, User user) {
    if (!update.hasCallbackQuery()) {
      throw new TelegramException("Что-то пошло не так", update);
    }

    ActivityRate activityRate = ActivityRate.valueOf(update.getCallbackQuery().getData());
    user.setActivity(activityRate.getRate());

    return user;
  }

  @Override
  protected SendMessage createNextMessage(Update update, User user) {

    return SendMessage.builder()
        .chatId(update.getCallbackQuery().getMessage().getChatId())
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
        .keyboard(Collections.singletonList(Arrays.asList(deficitButton, normalButton, profitButton)))
        .build();
  }
}
