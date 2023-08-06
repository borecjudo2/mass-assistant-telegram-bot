package com.peka.massassistanttelegrambot.command.handler.impl;

import com.peka.massassistanttelegrambot.command.BotCommandsUtils;
import com.peka.massassistanttelegrambot.command.handler.BotCommandHandler;
import com.peka.massassistanttelegrambot.model.Emoji;
import com.peka.massassistanttelegrambot.model.Food;
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
public class StartBotCommandHandler extends BotCommandHandler {

  private static final String START = "/start";
  private static final String CALCULATE = "/calculate";
  private static final String HELP = "/help";
  private static final String CONFIG = "/config";
  private static final String LOCATION = "/location";
  private static final String SCHEDULE = "/schedule";

  @Override
  protected String getCommandName() {
    return START;
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
          .ateFoodsByDay(Collections.singletonList(
              Food.builder()
                  .calories(234)
                  .proteins(12)
                  .fats(5)
                  .carbohydrates(124)
                  .build())
          )
          .proteinsValue(1.5)
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
        .replyMarkup(createReplyKeyboardMarkup())
        .build();
  }

  private ReplyKeyboardMarkup createReplyKeyboardMarkup() {
    KeyboardButton startButton = KeyboardButton.builder()
        .text(START)
        .build();

    KeyboardButton configButton = KeyboardButton.builder()
        .text(CONFIG)
        .build();

    KeyboardButton locationButton = KeyboardButton.builder()
        .text(LOCATION)
        .build();

    KeyboardButton calculateButton = KeyboardButton.builder()
        .text(CALCULATE)
        .build();

    KeyboardButton scheduleButton = KeyboardButton.builder()
        .text(SCHEDULE)
        .build();

    KeyboardButton helpButton = KeyboardButton.builder()
        .text(HELP)
        .build();

    return ReplyKeyboardMarkup.builder()
        .resizeKeyboard(true)
        .keyboard(Arrays.asList(
            new KeyboardRow(Collections.singletonList(startButton)),
            new KeyboardRow(Collections.singletonList(configButton)),
            new KeyboardRow(Collections.singletonList(locationButton)),
            new KeyboardRow(Collections.singletonList(calculateButton)),
            new KeyboardRow(Collections.singletonList(scheduleButton)),
            new KeyboardRow(Collections.singletonList(helpButton))
        ))
        .build();
  }
}
