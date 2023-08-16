package com.peka.massassistanttelegrambot.message.handler.impl;

import com.peka.massassistanttelegrambot.utils.BotMessagesUtils;
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
public class ConfigFatProcentageBotMessageHandler extends BotMessageHandler {

  @Override
  protected boolean isNeededMessageType(Update update) {
    return update.hasCallbackQuery();
  }

  @Override
  protected MessageStep getMessageStep() {
    return MessageStep.CONFIG_FAT_PERCENTAGE;
  }

  @Override
  protected MessageStep getNextMessageStep(User user) {
    return MessageStep.CONFIG_PROTEINS;
  }

  @Override
  protected User fillUserData(Update update, User user) {
    CallbackMessages callbackMessage = CallbackMessages.valueOf(update.getCallbackQuery().getData());

    if (CallbackMessages.KEEP_CONFIG.equals(callbackMessage)) {
      return user;
    }

    user.setFatPercentageEnabled(CallbackMessages.ENABLE_CONFIG_FAT_PERCENTAGE.equals(callbackMessage));

    return user;
  }

  @Override
  protected SendMessage createNextMessage(Update update, User user) {
    return SendMessage.builder()
        .replyMarkup(createInlineKeyboardMarkup())
        .chatId(update.getCallbackQuery().getMessage().getChatId())
        .text(String.format(
            BotMessagesUtils.CONFIG_PROTEINS_MESSAGE,
            user.getProteinsValue(),
            Emoji.MEAT.getEmoji()
        ))
        .build();
  }

  private InlineKeyboardMarkup createInlineKeyboardMarkup() {
    InlineKeyboardButton keepButton = InlineKeyboardButton.builder()
        .text(String.valueOf(CallbackMessages.KEEP_CONFIG.getData()))
        .callbackData(CallbackMessages.KEEP_CONFIG.toString())
        .build();

    return InlineKeyboardMarkup.builder()
        .keyboard(List.of(
            Collections.singletonList(keepButton)
        ))
        .build();
  }
}
