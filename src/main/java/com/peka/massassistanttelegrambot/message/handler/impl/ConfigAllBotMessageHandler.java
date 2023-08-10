package com.peka.massassistanttelegrambot.message.handler.impl;

import com.peka.massassistanttelegrambot.message.BotMessagesUtils;
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
public class ConfigAllBotMessageHandler extends BotMessageHandler {

  @Override
  protected boolean isNeededMessageType(Update update) {
    return update.hasCallbackQuery();
  }

  @Override
  protected MessageStep getMessageStep() {
    return MessageStep.CONFIG_ALL;
  }

  @Override
  protected MessageStep getNextMessageStep() {
    return MessageStep.CONFIG_FAT_PERCENTAGE;
  }

  @Override
  protected User fillUserData(Update update, User user) {
    return user;
  }

  @Override
  protected SendMessage createNextMessage(Update update, User user) {
    return SendMessage.builder()
        .replyMarkup(createInlineKeyboardMarkup(user))
        .chatId(update.getCallbackQuery().getMessage().getChatId())
        .text(String.format(
            BotMessagesUtils.CONFIG_FAT_PERCENTAGE_MESSAGE,
            user.isFatPercentageEnabled() ? "Включено" : "Выключено",
            user.isFatPercentageEnabled() ? Emoji.DONE_CHECK.getEmoji() : Emoji.X.getEmoji()
        ))
        .build();
  }

  private InlineKeyboardMarkup createInlineKeyboardMarkup(User user) {
    InlineKeyboardButton keepButton = InlineKeyboardButton.builder()
        .text(String.valueOf(CallbackMessages.KEEP_CONFIG.getData()))
        .callbackData(CallbackMessages.KEEP_CONFIG.toString())
        .build();

    CallbackMessages callbackMessage = user.isFatPercentageEnabled() ? CallbackMessages.DISABLE_CONFIG_FAT_PERCENTAGE :
        CallbackMessages.ENABLE_CONFIG_FAT_PERCENTAGE;
    InlineKeyboardButton onOffButton = InlineKeyboardButton.builder()
        .text(String.valueOf(callbackMessage.getData()))
        .callbackData(callbackMessage.toString())
        .build();

    return InlineKeyboardMarkup.builder()
        .keyboard(Arrays.asList(
            Collections.singletonList(keepButton),
            Collections.singletonList(onOffButton)
        ))
        .build();
  }
}
