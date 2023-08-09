package com.peka.massassistanttelegrambot.command.handler.impl;

import com.peka.massassistanttelegrambot.command.BotCommandsUtils;
import com.peka.massassistanttelegrambot.command.handler.BotCommandHandler;
import com.peka.massassistanttelegrambot.exception.TelegramException;
import com.peka.massassistanttelegrambot.model.CallbackMessages;
import com.peka.massassistanttelegrambot.model.Emoji;
import com.peka.massassistanttelegrambot.model.LatestMessage;
import com.peka.massassistanttelegrambot.model.MessageStep;
import com.peka.massassistanttelegrambot.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Collections;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class ClearBotCommandHandler extends BotCommandHandler {

  private static final String CLEAR = "/clear";

  @Override
  protected String getCommandName() {
    return CLEAR;
  }

  @Override
  protected User fillUserData(Update update, User user) {
    if (user == null) {
      throw new TelegramException("Нажми на /start ты не зарегистрирован!", update);
    }

    LatestMessage latestMessage = LatestMessage.builder()
        .chatId(update.getMessage().getChatId())
        .messageStep(MessageStep.CLEAR_FOOD)
        .build();

    user.setLatestMessage(latestMessage);

    return user;
  }

  @Override
  protected SendMessage createMessage(Update update, User user) {
    return SendMessage.builder()
        .replyMarkup(createInlineKeyboardMarkup())
        .chatId(update.getMessage().getChatId())
        .text(String.format(
            BotCommandsUtils.CLEAR_FOOD_COMMAND_TEXT,
            Emoji.RAMEN.getEmoji())
        )
        .build();
  }

  private InlineKeyboardMarkup createInlineKeyboardMarkup() {
    InlineKeyboardButton yesButton = InlineKeyboardButton.builder()
        .text(CallbackMessages.CLEAR_FOOD.getData())
        .callbackData(CallbackMessages.CLEAR_FOOD.toString())
        .build();

    return InlineKeyboardMarkup.builder()
        .keyboard(Collections.singletonList(Collections.singletonList(yesButton)))
        .build();
  }
}
