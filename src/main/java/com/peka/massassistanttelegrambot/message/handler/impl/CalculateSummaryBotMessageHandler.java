package com.peka.massassistanttelegrambot.message.handler.impl;

import com.peka.massassistanttelegrambot.message.handler.BotMessageHandler;
import com.peka.massassistanttelegrambot.model.MessageStep;
import com.peka.massassistanttelegrambot.model.User;
import com.peka.massassistanttelegrambot.service.CalculateService;
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
public class CalculateSummaryBotMessageHandler extends BotMessageHandler {

  private final CalculateService calculateService;

  @Override
  protected boolean isNeededMessageType(Update update) {
    return update.hasCallbackQuery();
  }

  @Override
  protected MessageStep getMessageStep() {
    return MessageStep.CALCULATE_SUMMARY;
  }

  @Override
  protected MessageStep getNextMessageStep() {
    return MessageStep.CALCULATE_RESULT;
  }

  @Override
  protected User fillUserData(Update update, User user) {
    user.setCalculatedResult(calculateService.calculate(user));
    return user;
  }

  @Override
  protected SendMessage createNextMessage(Update update, User user) {
    return SendMessage.builder()
        .chatId(update.getCallbackQuery().getMessage().getChatId())
        .text(user.getCalculatedResult())
        .build();
  }
}
