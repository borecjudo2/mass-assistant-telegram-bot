package com.peka.massassistanttelegrambot.command.handler.impl;

import com.peka.massassistanttelegrambot.command.handler.BotCommandHandler;
import com.peka.massassistanttelegrambot.exception.TelegramException;
import com.peka.massassistanttelegrambot.model.Emoji;
import com.peka.massassistanttelegrambot.model.LatestMessage;
import com.peka.massassistanttelegrambot.model.MessageStep;
import com.peka.massassistanttelegrambot.model.User;
import com.peka.massassistanttelegrambot.utils.BotCommands;
import com.peka.massassistanttelegrambot.utils.BotCommandsUtils;
import com.peka.massassistanttelegrambot.utils.BotMessagesUtils;
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
public class AddBotCommandHandler extends BotCommandHandler {

  @Override
  protected String getCommandName() {
    return BotCommands.ADD;
  }

  @Override
  protected User fillUserData(Update update, User user) {
    if (user == null) {
      throw new TelegramException(BotMessagesUtils.USER_NOT_LOGIN, update, true);
    }

    LatestMessage latestMessage = LatestMessage.builder()
        .chatId(update.getMessage().getChatId())
        .messageStep(MessageStep.ADD_FOOD)
        .build();

    user.setLatestMessage(latestMessage);

    return user;
  }

  @Override
  protected SendMessage createMessage(Update update, User user) {
    return SendMessage.builder()
        .chatId(update.getMessage().getChatId())
        .text(String.format(
            BotCommandsUtils.ADD_FOOD_COMMAND_TEXT,
            Emoji.FORK.getEmoji(),
            Emoji.MEAT.getEmoji(),
            Emoji.NUT.getEmoji(),
            Emoji.RAMEN.getEmoji())
        )
        .build();
  }
}
