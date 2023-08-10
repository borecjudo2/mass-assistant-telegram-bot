package com.peka.massassistanttelegrambot.message.handler.impl;

import com.peka.massassistanttelegrambot.exception.TelegramException;
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
public class ConfigProteinsBotMessageHandler extends BotMessageHandler {

  @Override
  protected boolean isNeededMessageType(Update update) {
    return true;
  }

  @Override
  protected MessageStep getMessageStep() {
    return MessageStep.CONFIG_PROTEINS;
  }

  @Override
  protected MessageStep getNextMessageStep(User user) {
    return MessageStep.CONFIG_FATS;
  }

  @Override
  protected User fillUserData(Update update, User user) {
    if (update.hasCallbackQuery()) {
      return user;
    }

    double proteins = validateProteins(update);
    user.setProteinsValue(proteins);

    return user;
  }

  private double validateProteins(Update update) {
    try {
      double proteins = Double.parseDouble(update.getMessage().getText());

      if (proteins <= 0 || proteins >= 5) {
        throw new Exception();
      }
      return proteins;
    } catch (Exception exception) {
      throw new TelegramException("Ошибка валидации количества белка! Повторите еще раз!", update);
    }
  }

  @Override
  protected SendMessage createNextMessage(Update update, User user) {
    long chatId = update.hasCallbackQuery() ? update.getCallbackQuery().getMessage().getChatId() :
        update.getMessage().getChatId();

    return SendMessage.builder()
        .replyMarkup(createInlineKeyboardMarkup())
        .chatId(chatId)
        .text(String.format(
            BotMessagesUtils.CONFIG_FATS_MESSAGE,
            user.getFatsValue(),
            Emoji.NUT.getEmoji()
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
