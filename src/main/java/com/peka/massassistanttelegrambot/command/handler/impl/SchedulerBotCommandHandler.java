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
public class SchedulerBotCommandHandler extends BotCommandHandler {

  private static final String SCHEDULE = "/schedule";

  @Override
  protected String getCommandName() {
    return SCHEDULE;
  }

  @Override
  protected User fillUserData(Update update, User user) {
    if (user == null) {
      throw new TelegramException("Нажми на /start ты не зарегистрирован!", update, true);
    }

    if (user.getTimeZone() == null || user.getTimeZone().isBlank()) {
      throw new TelegramException("Нажми на /location я не знаю твою тайм зону!", update, true);
    }

    if (user.getCalculatedResult() == null) {
      throw new TelegramException("Нажми на /calculate я не знаю твою дневную норму!", update, true);
    }

    LatestMessage latestMessage = LatestMessage.builder()
        .chatId(update.getMessage().getChatId())
        .messageStep(MessageStep.CONFIG_DAY_RESULT_TASK)
        .build();

    user.setLatestMessage(latestMessage);

    return user;
  }

  @Override
  protected SendMessage createMessage(Update update, User user) {
    Emoji message = user.isResultTaskEnabled() ?  Emoji.DONE_CHECK : Emoji.X;

    return SendMessage.builder()
        .chatId(update.getMessage().getChatId())
        .text(String.format(
            BotCommandsUtils.SCHEDULER_COMMAND_TEXT,
            Emoji.TIMER_CLOCK.getEmoji(),
            message.getEmoji())
        )
        .replyMarkup(createInlineKeyboardMarkup())
        .build();
  }

  private InlineKeyboardMarkup createInlineKeyboardMarkup() {
    InlineKeyboardButton enableButton = InlineKeyboardButton.builder()
        .text(CallbackMessages.ENABLE_DAY_RESULT_TASK.getData())
        .callbackData(CallbackMessages.ENABLE_DAY_RESULT_TASK.toString())
        .build();

    InlineKeyboardButton disableButton = InlineKeyboardButton.builder()
        .text(CallbackMessages.DISABLE_DAY_RESULT_TASK.getData())
        .callbackData(CallbackMessages.DISABLE_DAY_RESULT_TASK.toString())
        .build();

    return InlineKeyboardMarkup.builder()
        .keyboard(Collections.singletonList(Arrays.asList(enableButton, disableButton)))
        .build();
  }
}
