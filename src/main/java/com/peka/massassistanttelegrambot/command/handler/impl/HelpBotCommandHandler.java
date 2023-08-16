package com.peka.massassistanttelegrambot.command.handler.impl;

import com.peka.massassistanttelegrambot.command.handler.BotCommandHandler;
import com.peka.massassistanttelegrambot.model.User;
import com.peka.massassistanttelegrambot.utils.BotCommands;
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
public class HelpBotCommandHandler extends BotCommandHandler {

  @Override
  protected String getCommandName() {
    return BotCommands.HELP;
  }

  @Override
  protected User fillUserData(Update update, User user) {
    return user;
  }

  @Override
  protected SendMessage createMessage(Update update, User user) {
    return SendMessage.builder()
        .chatId(update.getMessage().getChatId())
        .text(BotMessagesUtils.HELP_MESSAGE)
        .parseMode(BotCommands.MARKDOWN)
        .build();
  }
}
