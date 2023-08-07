package com.peka.massassistanttelegrambot.message.handler.impl;

import com.peka.massassistanttelegrambot.exception.TelegramException;
import com.peka.massassistanttelegrambot.message.BotMessagesUtils;
import com.peka.massassistanttelegrambot.message.handler.BotMessageHandler;
import com.peka.massassistanttelegrambot.model.Emoji;
import com.peka.massassistanttelegrambot.model.Food;
import com.peka.massassistanttelegrambot.model.MessageStep;
import com.peka.massassistanttelegrambot.model.User;
import com.peka.massassistanttelegrambot.scheduler.DayResultSchedulerService;
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
public class AddBotMessageHandler extends BotMessageHandler {

  private final DayResultSchedulerService schedulerService;

  @Override
  protected boolean isNeededMessageType(Update update) {
    return update.hasMessage();
  }

  @Override
  protected MessageStep getMessageStep() {
    return MessageStep.ADD_FOOD;
  }

  @Override
  protected MessageStep getNextMessageStep() {
    return MessageStep.START;
  }

  @Override
  protected User fillUserData(Update update, User user) {
    Food food = parseData(update);
    if (food.getCalories() < 0) {
      throw new TelegramException("Такого значения калорий быть не может!", update);
    }
    if (food.getProteins() < 0) {
      throw new TelegramException("Такого значения белка быть не может!", update);
    }
    if (food.getFats() < 0) {
      throw new TelegramException("Такого значения жиров быть не может!", update);
    }
    if (food.getCarbohydrates() < 0) {
      throw new TelegramException("Такого значения углеводов быть не может!", update);
    }

    user.getAteFoodsByDay().add(food);

    return user;
  }

  private Food parseData(Update update) {
    try {
      String[] values = update.getMessage().getText().split("\n");
      return Food.builder()
          .calories(Double.parseDouble(values[0]))
          .proteins(Double.parseDouble(values[1]))
          .fats(Double.parseDouble(values[2]))
          .carbohydrates(Double.parseDouble(values[3]))
          .build();
    } catch (Exception exception) {
      throw new TelegramException("В неправильном формате отправлено сообщение об еде", update);
    }
  }

  @Override
  protected SendMessage createNextMessage(Update update, User user) {
    Food food = user.getAteFoodsByDay().get(user.getAteFoodsByDay().size() - 1);

    return SendMessage.builder()
        .chatId(update.getMessage().getChatId())
        .text(String.format(
            BotMessagesUtils.ADDED_FOOD_MESSAGE,
            food.getCalories(),
            Emoji.FORK.getEmoji(),
            food.getProteins(),
            Emoji.MEAT.getEmoji(),
            food.getFats(),
            Emoji.NUT.getEmoji(),
            food.getCarbohydrates(),
            Emoji.RAMEN.getEmoji()
        ))
        .build();
  }
}
