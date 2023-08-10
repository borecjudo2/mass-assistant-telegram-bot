package com.peka.massassistanttelegrambot.message.handler.impl;

import com.peka.massassistanttelegrambot.message.BotMessagesUtils;
import com.peka.massassistanttelegrambot.message.handler.BotMessageHandler;
import com.peka.massassistanttelegrambot.model.Emoji;
import com.peka.massassistanttelegrambot.model.MessageStep;
import com.peka.massassistanttelegrambot.model.Sex;
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
public class CalculateAgainBotMessageHandler extends BotMessageHandler {

  @Override
  protected boolean isNeededMessageType(Update update) {
    return update.hasCallbackQuery();
  }

  @Override
  protected MessageStep getMessageStep() {
    return MessageStep.CALCULATE_RESULT;
  }

  @Override
  protected MessageStep getNextMessageStep(User user) {
    return MessageStep.CALCULATE_SEX;
  }

  @Override
  protected User fillUserData(Update update, User user) {
    return user;
  }

  @Override
  protected SendMessage createNextMessage(Update update, User user) {
    return SendMessage.builder()
        .chatId(update.getCallbackQuery().getMessage().getChatId())
        .text(String.format(
            BotMessagesUtils.CALCULATE_SEX_MESSAGE,
            Emoji.MAN_MAGE.getEmoji(),
            Emoji.WOMAN_MAGE.getEmoji()
        ))
        .replyMarkup(createInlineKeyboardMarkup())
        .build();
  }

  private InlineKeyboardMarkup createInlineKeyboardMarkup() {
    InlineKeyboardButton manButton = InlineKeyboardButton.builder()
        .text(Sex.MAN.getValue())
        .callbackData(Sex.MAN.toString())
        .build();

    InlineKeyboardButton womanButton = InlineKeyboardButton.builder()
        .text(Sex.WOMAN.getValue())
        .callbackData(Sex.WOMAN.toString())
        .build();

    return InlineKeyboardMarkup.builder()
        .keyboard(Collections.singletonList(Arrays.asList(manButton, womanButton)))
        .build();
  }
}
