package com.peka.massassistanttelegrambot.message.handler.impl;

import com.peka.massassistanttelegrambot.exception.TelegramException;
import com.peka.massassistanttelegrambot.message.BotMessagesUtils;
import com.peka.massassistanttelegrambot.message.handler.BotMessageHandler;
import com.peka.massassistanttelegrambot.model.CallbackMessages;
import com.peka.massassistanttelegrambot.model.Emoji;
import com.peka.massassistanttelegrambot.model.Food;
import com.peka.massassistanttelegrambot.model.MessageStep;
import com.peka.massassistanttelegrambot.model.User;
import com.peka.massassistanttelegrambot.service.CalculateService;
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
public class AddBotMessageHandler extends BotMessageHandler {

  private final CalculateService calculateService;

  @Override
  protected boolean isNeededMessageType(Update update) {
    return update.hasMessage();
  }

  @Override
  protected MessageStep getMessageStep() {
    return MessageStep.ADD_FOOD;
  }

  @Override
  protected MessageStep getNextMessageStep(User user) {
    return MessageStep.START;
  }

  @Override
  protected User fillUserData(Update update, User user) {
    Food food = parseData(update);

    if (food.getName() == null || food.getName().isBlank()) {
      throw new TelegramException("Такого значения имени быть не может!", update, true);
    }
    if (food.getProteins() < 0) {
      throw new TelegramException("Такого значения белка быть не может!", update, true);
    }
    if (food.getFats() < 0) {
      throw new TelegramException("Такого значения жиров быть не может!", update, true);
    }
    if (food.getCarbohydrates() < 0) {
      throw new TelegramException("Такого значения углеводов быть не может!", update, true);
    }

    user.getAteFoodsByDay().add(calculateService.calcylateCaloriesForFood(food));

    return user;
  }

  private Food parseData(Update update) {
    try {
      String[] values = update.getMessage().getText().split("\n");
      return Food.builder()
          .name(values[0])
          .proteins(Double.parseDouble(values[1]))
          .fats(Double.parseDouble(values[2]))
          .carbohydrates(Double.parseDouble(values[3]))
          .build();
    } catch (Exception exception) {
      throw new TelegramException("В неправильном формате отправлено сообщение об еде", update, true);
    }
  }

  @Override
  protected SendMessage createNextMessage(Update update, User user) {
    List<Food> ateFoodsByDay = user.getAteFoodsByDay();
    Food food = ateFoodsByDay.get(ateFoodsByDay.size() - 1);

    return SendMessage.builder()
        .chatId(update.getMessage().getChatId())
        .text(String.format(
            BotMessagesUtils.ADDED_FOOD_MESSAGE,
            food.getName(),
            food.getCalories(),
            Emoji.FORK.getEmoji(),
            food.getProteins(),
            Emoji.MEAT.getEmoji(),
            food.getFats(),
            Emoji.NUT.getEmoji(),
            food.getCarbohydrates(),
            Emoji.RAMEN.getEmoji()
        ))
        .replyMarkup(createInlineKeyboardMarkup(food))
        .build();
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
