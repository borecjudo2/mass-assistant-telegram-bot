package com.peka.massassistanttelegrambot.command.handler.impl;

import com.peka.massassistanttelegrambot.command.handler.BotCommandHandler;
import com.peka.massassistanttelegrambot.model.Emoji;
import com.peka.massassistanttelegrambot.model.LatestMessage;
import com.peka.massassistanttelegrambot.model.MessageStep;
import com.peka.massassistanttelegrambot.model.User;
import com.peka.massassistanttelegrambot.utils.BotCommands;
import com.peka.massassistanttelegrambot.utils.BotCommandsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class StartBotCommandHandler extends BotCommandHandler {

  @Override
  protected String getCommandName() {
    return BotCommands.START;
  }

  @Override
  protected User fillUserData(Update update, User user) {
    LatestMessage latestMessage = LatestMessage.builder()
        .chatId(update.getMessage().getChatId())
        .messageStep(MessageStep.START)
        .build();

    if (user == null) {
      return User.builder()
          .id(update.getMessage().getChatId())
          .username(update.getMessage().getChat().getUserName())
          .latestMessage(latestMessage)
          .ateFoodsByDay(Collections.emptyList())
          .likedFoods(Collections.emptySet())
          .isFatPercentageEnabled(false)
          .fatsValue(1)
          .proteinsValue(1.5)
          .isResultTaskEnabled(false)
          .build();
    }

    user.setLatestMessage(latestMessage);

    return user;
  }

  @Override
  protected SendMessage createMessage(Update update, User user) {
    return SendMessage.builder()
        .chatId(update.getMessage().getChatId())
        .text(
            String.format(
                BotCommandsUtils.START_COMMAND_TEXT,
                update.getMessage().getChat().getUserName(),
                Emoji.WAVE.getEmoji(),
                Emoji.SPAGHETTI.getEmoji()
            )
        )
        .build();
  }
}
