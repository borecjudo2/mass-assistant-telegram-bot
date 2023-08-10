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

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class CalculateSexBotMessageHandler extends BotMessageHandler {

  @Override
  protected boolean isNeededMessageType(Update update) {
    return update.hasCallbackQuery();
  }

  @Override
  protected MessageStep getMessageStep() {
    return MessageStep.CALCULATE_SEX;
  }

  @Override
  protected MessageStep getNextMessageStep(User user) {
    return MessageStep.CALCULATE_AGE;
  }

  @Override
  protected User fillUserData(Update update, User user) {
    Sex sex = Sex.valueOf(update.getCallbackQuery().getData());
    user.setSex(sex);
    return user;
  }

  @Override
  protected SendMessage createNextMessage(Update update, User user) {
    long chatId = update.hasMessage() ?
        update.getMessage().getChatId() :
        update.getCallbackQuery().getMessage().getChatId();

    return SendMessage.builder()
        .chatId(chatId)
        .text(String.format(
            BotMessagesUtils.CALCULATE_AGE_MESSAGE,
            Emoji.UNDERAGE.getEmoji()
        ))
        .build();
  }
}
