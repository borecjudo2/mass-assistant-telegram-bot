package com.peka.massassistanttelegrambot.service.impl;

import com.peka.massassistanttelegrambot.exception.TelegramException;
import com.peka.massassistanttelegrambot.model.Sex;
import com.peka.massassistanttelegrambot.model.User;
import com.peka.massassistanttelegrambot.repo.UserRepository;
import com.peka.massassistanttelegrambot.service.BotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class BotServiceImpl implements BotService {

  private final TelegramLongPollingBot bot;
  private final UserRepository userRepository;

  @Override
  public void processUpdate(Update update) {
    try {
      Message receivedMessage = update.getMessage();
      String receivedText = receivedMessage.getText();

      if (receivedText.isBlank()) {
        SendMessage messageIsBlank = SendMessage.builder()
            .chatId(receivedMessage.getChatId())
            .text("Ваше сообщение пустое, пожалуйста, исправьте это!")
            .build();
        bot.execute(messageIsBlank);
      }

      String[] wordsList = receivedText.split(" ");

      if (wordsList[0].equalsIgnoreCase("Данные")) {
        User userToSave = User.builder()
            .username(receivedMessage.getChat().getUserName())
            .sex(Sex.getByValue(findNeededWordValue(wordsList, "Пол").toUpperCase()))
            .age(Integer.parseInt(findNeededWordValue(wordsList, "Возраст")))
            .height(Integer.parseInt(findNeededWordValue(wordsList, "Рост")))
            .weight(Integer.parseInt(findNeededWordValue(wordsList, "Вес")))
            .targetWeight(Integer.parseInt(findNeededWordValue(wordsList, "ВесЦель")))
            .activity(Double.parseDouble(findNeededWordValue(wordsList, "Активность")))
            .build();

        userRepository.saveUser(userToSave);

        SendMessage messageIsBlank = SendMessage.builder()
            .chatId(receivedMessage.getChatId())
            .text(userRepository.getUserByUsername(receivedMessage.getChat().getUserName()).toString())
            .build();
        bot.execute(messageIsBlank);
        return;
      }

      if (wordsList[0].equalsIgnoreCase("Программа")) {
        User existingUser = userRepository.getUserByUsername(receivedMessage.getChat().getUserName());

        SendMessage messageIsBlank = SendMessage.builder()
            .chatId(receivedMessage.getChatId())
            .text(calculate(existingUser))
            .build();

        bot.execute(messageIsBlank);
        return;
      }

      SendMessage messageIsBlank = SendMessage.builder()
          .chatId(receivedMessage.getChatId())
          .text(userRepository.getUserByUsername(receivedMessage.getChat().getUserName()).toString())
          .build();
      bot.execute(messageIsBlank);
    } catch (Exception exception) {
      throw new TelegramException(update, exception);
    }
  }

  private String findNeededWordValue(String[] wordsList, String targetWord) {
    for (String word : wordsList) {
      String[] values = word.split("=");
      if (values[0].equalsIgnoreCase(targetWord)) {
        return values[1];
      }
    }

    throw new RuntimeException(targetWord + " не найден!");
  }

  private String calculate(User user) {
    double normalCalories =
        20 * user.getTargetWeight() + 6.25 * user.getHeight() - 5 * user.getAge() + user.getSex().getData();

    double activityCalories = normalCalories * user.getActivity();

    int userTargetWeight = user.getTargetWeight();
    double proteins = 2.0 * userTargetWeight;
    double fats = 1.0 * userTargetWeight;
    double carbohydrates = (activityCalories - proteins * 4.2 - fats * 9.29) / 4.2;

    return String.format("Вам нужно %.1f К.\nИз них:\nБелки: %.1f гр\nЖиры %.1f гр\nУглеводы: %.1f гр",
        activityCalories, proteins, fats, carbohydrates);
  }
}
