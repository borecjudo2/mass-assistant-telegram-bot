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
        .replyMarkup(createReplyKeyboardMarkup())
        .build();
  }

  private ReplyKeyboardMarkup createReplyKeyboardMarkup() {
    KeyboardButton startButton = KeyboardButton.builder()
        .text(BotCommands.START)
        .build();

    KeyboardButton configButton = KeyboardButton.builder()
        .text(BotCommands.CONFIG)
        .build();

    KeyboardButton locationButton = KeyboardButton.builder()
        .text(BotCommands.LOCATION)
        .build();

    KeyboardButton calculateButton = KeyboardButton.builder()
        .text(BotCommands.CALCULATE)
        .build();

    KeyboardButton scheduleButton = KeyboardButton.builder()
        .text(BotCommands.SCHEDULE)
        .build();

    KeyboardButton addButton = KeyboardButton.builder()
        .text(BotCommands.ADD)
        .build();

    KeyboardButton likedListButton = KeyboardButton.builder()
        .text(BotCommands.LIKED_LIST)
        .build();

    KeyboardButton clearButton = KeyboardButton.builder()
        .text(BotCommands.CLEAR)
        .build();

    KeyboardButton resultButton = KeyboardButton.builder()
        .text(BotCommands.RESULT)
        .build();

    KeyboardButton helpButton = KeyboardButton.builder()
        .text(BotCommands.HELP)
        .build();

    return ReplyKeyboardMarkup.builder()
        .resizeKeyboard(true)
        .keyboard(Arrays.asList(
            new KeyboardRow(Collections.singletonList(startButton)),
            new KeyboardRow(Collections.singletonList(configButton)),
            new KeyboardRow(Collections.singletonList(locationButton)),
            new KeyboardRow(Collections.singletonList(calculateButton)),
            new KeyboardRow(Collections.singletonList(scheduleButton)),
            new KeyboardRow(Collections.singletonList(addButton)),
            new KeyboardRow(Collections.singletonList(likedListButton)),
            new KeyboardRow(Collections.singletonList(clearButton)),
            new KeyboardRow(Collections.singletonList(resultButton)),
            new KeyboardRow(Collections.singletonList(helpButton))
        ))
        .build();
  }
}
