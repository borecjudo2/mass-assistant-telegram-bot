package com.peka.massassistanttelegrambot.message.handler.impl;

import com.peka.massassistanttelegrambot.command.BotCommandsUtils;
import com.peka.massassistanttelegrambot.exception.TelegramException;
import com.peka.massassistanttelegrambot.message.handler.BotMessageHandler;
import com.peka.massassistanttelegrambot.model.CallbackMessages;
import com.peka.massassistanttelegrambot.model.Emoji;
import com.peka.massassistanttelegrambot.model.MessageStep;
import com.peka.massassistanttelegrambot.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Collections;
import java.util.List;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class ConfigFatsBotMessageHandler extends BotMessageHandler {

  @Override
  protected boolean isNeededMessageType(Update update) {
    return true;
  }

  @Override
  protected MessageStep getMessageStep() {
    return MessageStep.CONFIG_FATS;
  }

  @Override
  protected MessageStep getNextMessageStep(User user) {
    return MessageStep.CONFIG_ALL;
  }

  @Override
  protected User fillUserData(Update update, User user) {
    if (update.hasCallbackQuery()) {
      return user;
    }

    double fats = validateFats(update);
    user.setFatsValue(fats);

    return user;
  }

  private double validateFats(Update update) {
    try {
      double fats = Double.parseDouble(update.getMessage().getText());

      if (fats <= 0 || fats >= 5) {
        throw new Exception();
      }
      return fats;
    } catch (Exception exception) {
      throw new TelegramException("Ошибка валидации количества жиров! Повторите еще раз!", update);
    }
  }

  @Override
  protected SendMessage createNextMessage(Update update, User user) {
    long chatId = update.hasCallbackQuery() ? update.getCallbackQuery().getMessage().getChatId() :
        update.getMessage().getChatId();

    return SendMessage.builder()
        .chatId(chatId)
        .text(String.format(
            BotCommandsUtils.CONFIG_ALL_COMMAND_TEXT,
            Emoji.FORK.getEmoji(),
            user.isFatPercentageEnabled() ? "Включено" : "Выключено",
            user.isFatPercentageEnabled() ? Emoji.DONE_CHECK.getEmoji() : Emoji.X.getEmoji(),
            user.getProteinsValue(),
            Emoji.MEAT.getEmoji(),
            user.getFatsValue(),
            Emoji.NUT.getEmoji()
        ))
        .replyMarkup(createInlineKeyboardMarkup())
        .build();
  }

  private InlineKeyboardMarkup createInlineKeyboardMarkup() {
    InlineKeyboardButton changeConfigButton = InlineKeyboardButton.builder()
        .text(CallbackMessages.CHANGE_CONFIG.getData())
        .callbackData(CallbackMessages.CHANGE_CONFIG.toString())
        .build();

    return InlineKeyboardMarkup.builder()
        .keyboard(List.of(
            Collections.singletonList(changeConfigButton)
        ))
        .build();
  }
}
