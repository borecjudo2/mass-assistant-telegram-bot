package com.peka.massassistanttelegrambot.command.handler.impl;

import com.peka.massassistanttelegrambot.command.handler.BotCommandHandler;
import com.peka.massassistanttelegrambot.exception.TelegramException;
import com.peka.massassistanttelegrambot.model.CallbackMessages;
import com.peka.massassistanttelegrambot.model.LatestMessage;
import com.peka.massassistanttelegrambot.model.MessageStep;
import com.peka.massassistanttelegrambot.model.User;
import com.peka.massassistanttelegrambot.utils.BotCommands;
import com.peka.massassistanttelegrambot.utils.BotMessagesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Collections;
import java.util.List;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class LikedListBotCommandHandler extends BotCommandHandler {

  @Override
  protected String getCommandName() {
    return BotCommands.LIKED_LIST;
  }

  @Override
  protected User fillUserData(Update update, User user) {
    if (user == null) {
      throw new TelegramException(BotMessagesUtils.USER_NOT_LOGIN, update, true);
    }

    LatestMessage latestMessage = LatestMessage.builder()
        .chatId(update.getMessage().getChatId())
        .messageStep(MessageStep.LIKED_LIST)
        .build();

    user.setLatestMessage(latestMessage);

    return user;
  }

  @Override
  protected SendMessage createMessage(Update update, User user) {
    if (user.getLikedFoods().isEmpty()) {
      return SendMessage.builder()
          .chatId(update.getMessage().getChatId())
          .text(BotMessagesUtils.EMPTY_LIKED_FOODS)
          .build();
    }

    return SendMessage.builder()
        .chatId(update.getMessage().getChatId())
        .text(BotMessagesUtils.LIST_LIKED_FOODS)
        .replyMarkup(createInlineKeyboardMarkup(user))
        .build();
  }

  private InlineKeyboardMarkup createInlineKeyboardMarkup(User user) {
    List<List<InlineKeyboardButton>> buttons = user.getLikedFoods().stream().map(food -> InlineKeyboardButton.builder()
            .text(food.getName())
            .callbackData(CallbackMessages.OPEN_FOOD_DETAILS + CallbackMessages.CALLBACK_SPLITTER.getData() + food.getName())
            .build())
        .map(Collections::singletonList)
        .toList();

    return InlineKeyboardMarkup.builder()
        .keyboard(buttons)
        .build();
  }
}
