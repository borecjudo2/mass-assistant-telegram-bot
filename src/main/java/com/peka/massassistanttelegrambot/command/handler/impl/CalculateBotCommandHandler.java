package com.peka.massassistanttelegrambot.command.handler.impl;

import com.peka.massassistanttelegrambot.command.BotCommandsUtils;
import com.peka.massassistanttelegrambot.command.handler.BotCommandHandler;
import com.peka.massassistanttelegrambot.model.CallbackMessages;
import com.peka.massassistanttelegrambot.model.LatestMessage;
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
public class CalculateBotCommandHandler extends BotCommandHandler {

  private static final String CALCULATE = "/calculate";

  @Override
  protected String getCommandName() {
    return CALCULATE;
  }

  @Override
  protected User fillUserData(Update update, User user) {
    MessageStep messageStep = user.getCalculatedResult() == null ? MessageStep.CALCULATE_SEX :
        MessageStep.CALCULATE_RESULT;

    LatestMessage latestMessage = LatestMessage.builder()
        .chatId(update.getMessage().getChatId())
        .messageStep(messageStep)
        .build();

    user.setLatestMessage(latestMessage);

    return user;
  }

  @Override
  protected SendMessage createMessage(Update update, User user) {
    if (user.getCalculatedResult() == null) {
      return SendMessage.builder()
          .chatId(update.getMessage().getChatId())
          .text(BotCommandsUtils.CALCULATE_COMMAND_TEXT)
          .replyMarkup(createInlineKeyboardMarkup())
          .build();
    }

    return SendMessage.builder()
        .chatId(update.getMessage().getChatId())
        .text(user.getCalculatedResult())
        .replyMarkup(createInlineKeyboardMarkupForNewCalculation())
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

  private InlineKeyboardMarkup createInlineKeyboardMarkupForNewCalculation() {
    InlineKeyboardButton manButton = InlineKeyboardButton.builder()
        .text(CallbackMessages.CALCULATE_AGAIN.getData())
        .callbackData(CallbackMessages.CALCULATE_AGAIN.toString())
        .build();

    return InlineKeyboardMarkup.builder()
        .keyboard(Collections.singletonList(Collections.singletonList(manButton)))
        .build();
  }
}
