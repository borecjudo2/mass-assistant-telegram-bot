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

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class ConfigProteinsDefaultBotMessageHandler extends BotMessageHandler {

  @Override
  protected boolean isNeededMessageType(Update update) {
    return update.hasCallbackQuery();
  }

  @Override
  protected MessageStep getMessageStep() {
    return MessageStep.CONFIG_PROTEINS;
  }

  @Override
  protected MessageStep getNextMessageStep() {
    return MessageStep.CONFIG_PROTEINS;
  }

  @Override
  protected User fillUserData(Update update, User user) {
    CallbackMessages proteinsValue = CallbackMessages.valueOf(update.getCallbackQuery().getData());
    user.setProteinsValue(Double.parseDouble(proteinsValue.getData()));
    return user;
  }

  @Override
  protected SendMessage createNextMessage(Update update, User user) {
    return SendMessage.builder()
        .chatId(update.getCallbackQuery().getMessage().getChatId())
        .text(String.format(
            BotMessagesUtils.CONFIG_PROTEINS_MESSAGE,
            user.getProteinsValue(),
            Emoji.MEAT.getEmoji()
        ))
        .build();
  }
}
