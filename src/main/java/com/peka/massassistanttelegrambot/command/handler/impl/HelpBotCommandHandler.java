package com.peka.massassistanttelegrambot.command.handler.impl;

import com.peka.massassistanttelegrambot.command.BotCommandsUtils;
import com.peka.massassistanttelegrambot.command.handler.BotCommandHandler;
import com.peka.massassistanttelegrambot.model.LatestMessage;
import com.peka.massassistanttelegrambot.model.MessageStep;
import com.peka.massassistanttelegrambot.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

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
public class HelpBotCommandHandler extends BotCommandHandler {
  private static final String HELP = "/help";

  @Override
  protected String getCommandName() {
    return HELP;
  }

  @Override
  protected User fillUserData(Update update, User user) {
    return user;
  }

  @Override
  protected SendMessage createMessage(Update update, User user) {
    // TODO: 7/31/2023 impl help flow
    return SendMessage.builder()
        .chatId(update.getMessage().getChatId())
        .text("Когда-то здесь что-то будет!)")
        .build();
  }
}
