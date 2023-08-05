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
public class ConfigBotCommandHandler extends BotCommandHandler {

  private static final String CONFIG = "/config";

  @Override
  protected String getCommandName() {
    return CONFIG;
  }

  @Override
  protected User fillUserData(Update update, User user) {
    if (user == null) {
      throw new TelegramException("Нажми на /start ты не зарегистрирован!", update);
    }

    LatestMessage latestMessage = LatestMessage.builder()
        .chatId(update.getMessage().getChatId())
        .messageStep(MessageStep.CONFIG_PROTEINS)
        .build();

    user.setLatestMessage(latestMessage);

    return user;
  }

  @Override
  protected SendMessage createMessage(Update update, User user) {
    return SendMessage.builder()
        .chatId(update.getMessage().getChatId())
        .text(String.format(
            BotCommandsUtils.CONFIG_PROTEINS_COMMAND_TEXT,
            Emoji.MEAT.getEmoji(),
            user.getProteinsValue(),
            Emoji.MEAT.getEmoji()
        ))
        .replyMarkup(createInlineKeyboardMarkup())
        .build();
  }

  private InlineKeyboardMarkup createInlineKeyboardMarkup() {
    InlineKeyboardButton proteinsDefault = InlineKeyboardButton.builder()
        .text(CallbackMessages.CONFIG_PROTEINS_1_5.getData())
        .callbackData(CallbackMessages.CONFIG_PROTEINS_1_5.toString())
        .build();

    InlineKeyboardButton proteinsHyper = InlineKeyboardButton.builder()
        .text(CallbackMessages.CONFIG_PROTEINS_2.getData())
        .callbackData(CallbackMessages.CONFIG_PROTEINS_2.toString())
        .build();

    return InlineKeyboardMarkup.builder()
        .keyboard(Collections.singletonList(Arrays.asList(proteinsDefault, proteinsHyper)))
        .build();
  }
}
