package com.peka.massassistanttelegrambot.message.handler.impl;

import com.peka.massassistanttelegrambot.message.handler.BotMessageHandler;
import com.peka.massassistanttelegrambot.model.CallbackMessages;
import com.peka.massassistanttelegrambot.model.Emoji;
import com.peka.massassistanttelegrambot.model.MessageStep;
import com.peka.massassistanttelegrambot.model.User;
import com.peka.massassistanttelegrambot.scheduler.DayResultSchedulerService;
import com.peka.massassistanttelegrambot.utils.BotMessagesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@ConditionalOnProperty(name = "service.location.enabled", havingValue = "true")
@RequiredArgsConstructor
public class DayResultTaskBotMessageHandler extends BotMessageHandler {

  private final DayResultSchedulerService schedulerService;

  @Override
  protected boolean isNeededMessageType(Update update) {
    return update.hasCallbackQuery();
  }

  @Override
  protected MessageStep getMessageStep() {
    return MessageStep.CONFIG_DAY_RESULT_TASK;
  }

  @Override
  protected MessageStep getNextMessageStep(User user) {
    return MessageStep.START;
  }

  @Override
  protected User fillUserData(Update update, User user) {
    CallbackMessages message = CallbackMessages.valueOf(update.getCallbackQuery().getData());

    user.setResultTaskEnabled(message.equals(CallbackMessages.ENABLE_DAY_RESULT_TASK));

    return user;
  }

  @Override
  protected SendMessage createNextMessage(Update update, User user) {
    CallbackMessages message = CallbackMessages.valueOf(update.getCallbackQuery().getData());

    if (message.equals(CallbackMessages.ENABLE_DAY_RESULT_TASK)) {
      schedulerService.schedule(user.getId(), user.getTimeZone());

      return SendMessage.builder()
          .chatId(update.getCallbackQuery().getMessage().getChatId())
          .text(String.format(
              BotMessagesUtils.SCHEDULER_ENABLED_MESSAGE,
              Emoji.DONE_CHECK.getEmoji(),
              Emoji.TIMER_CLOCK.getEmoji()
          ))
          .build();
    }

    schedulerService.deleteSchedule(user.getId());

    return SendMessage.builder()
        .chatId(update.getCallbackQuery().getMessage().getChatId())
        .text(String.format(
            BotMessagesUtils.SCHEDULER_DISABLED_MESSAGE,
            Emoji.X.getEmoji()
        ))
        .build();
  }
}
