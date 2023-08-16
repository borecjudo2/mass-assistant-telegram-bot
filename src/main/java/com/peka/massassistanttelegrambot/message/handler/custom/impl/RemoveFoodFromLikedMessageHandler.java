package com.peka.massassistanttelegrambot.message.handler.custom.impl;

import com.peka.massassistanttelegrambot.exception.TelegramException;
import com.peka.massassistanttelegrambot.message.handler.custom.BotCustomMessageHandler;
import com.peka.massassistanttelegrambot.model.CallbackMessages;
import com.peka.massassistanttelegrambot.model.ExceptionMessage;
import com.peka.massassistanttelegrambot.model.Food;
import com.peka.massassistanttelegrambot.model.User;
import com.peka.massassistanttelegrambot.repo.MongodbUserRepository;
import com.peka.massassistanttelegrambot.utils.BotMessagesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class RemoveFoodFromLikedMessageHandler implements BotCustomMessageHandler {

  private final MongodbUserRepository userRepository;
  private final TelegramLongPollingBot bot;

  @Override
  public boolean isMyMessage(Update update, User user) {
    return update.hasCallbackQuery()
           && update.getCallbackQuery().getData().contains(CallbackMessages.FOOD_REMOVE_FROM_LIKED.toString());
  }

  @Override
  public User handleMessage(Update update, User user) {
    try {
      if (user == null) {
        throw new TelegramException(BotMessagesUtils.USER_NOT_LOGIN, update, true);
      }

      String foodName = update.getCallbackQuery().getData().split(CallbackMessages.CALLBACK_SPLITTER.getData())[1];
      User existingUser = userRepository.findById(user.getId()).orElseThrow();
      Food existingFood = existingUser.getLikedFoods().stream()
          .filter(food -> food.getName().equals(foodName))
          .findFirst()
          .orElseThrow(() -> new TelegramException(ExceptionMessage.ERROR_DELETED_FOOD.getText(), update, true));

      user.getLikedFoods().remove(existingFood);

      sendUpdatedMessage(update, existingFood);

      return user;
    } catch (TelegramApiException exception) {
      throw new TelegramException(update, exception, false);
    }
  }

  protected void sendUpdatedMessage(Update update, Food food) throws TelegramApiException {
    DeleteMessage deleteMessage = DeleteMessage.builder()
        .chatId(update.getCallbackQuery().getMessage().getChatId())
        .messageId(update.getCallbackQuery().getMessage().getMessageId())
        .build();

    bot.execute(deleteMessage);

    SendMessage sendMessage = SendMessage.builder()
        .chatId(update.getCallbackQuery().getMessage().getChatId())
        .text(update.getCallbackQuery().getMessage().getText())
        .replyMarkup(createInlineKeyboardMarkup(food))
        .build();

    bot.execute(sendMessage);
  }

  private InlineKeyboardMarkup createInlineKeyboardMarkup(Food food) {
    InlineKeyboardButton addToLikedButton = InlineKeyboardButton.builder()
        .text(CallbackMessages.FOOD_ADD_TO_LIKED.getData())
        .callbackData(CallbackMessages.FOOD_ADD_TO_LIKED + CallbackMessages.CALLBACK_SPLITTER.getData() + food.getName())
        .build();

    return InlineKeyboardMarkup.builder()
        .keyboard(Collections.singletonList(Collections.singletonList(addToLikedButton)))
        .build();
  }
}
