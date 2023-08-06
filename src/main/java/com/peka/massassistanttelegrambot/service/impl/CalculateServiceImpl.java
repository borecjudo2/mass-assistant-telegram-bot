package com.peka.massassistanttelegrambot.service.impl;

import com.peka.massassistanttelegrambot.message.BotMessagesUtils;
import com.peka.massassistanttelegrambot.model.CalculateResult;
import com.peka.massassistanttelegrambot.model.Emoji;
import com.peka.massassistanttelegrambot.model.Food;
import com.peka.massassistanttelegrambot.model.User;
import com.peka.massassistanttelegrambot.service.CalculateService;
import org.springframework.stereotype.Service;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Service
public class CalculateServiceImpl implements CalculateService {

  @Override
  public CalculateResult calculate(User user) {
    double normalCalories =
        10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() + user.getSex().getData();

    double activityCalories = normalCalories * user.getActivity() * user.getCalculateType().getValue();

    double userTargetWeight = user.getWeight();
    double proteins = user.getProteinsValue() * userTargetWeight;
    double fats = 1.0 * userTargetWeight;
    double carbohydrates = (activityCalories - proteins * 4.2 - fats * 9.29) / 4.2;

    return CalculateResult.builder()
        .activityCalories(activityCalories)
        .proteins(proteins)
        .fats(fats)
        .carbohydrates(carbohydrates)
        .build();
  }

  @Override
  public String calculateDayResult(User user) {
    if (user.getAteFoodsByDay() == null || user.getAteFoodsByDay().isEmpty()) {
      return BotMessagesUtils.ERROR_NO_ATE_FOOD_FOR_DAY_RESULT_TASK;
    }

    double dayCalories = 0;
    double dayProteins = 0;
    double dayFats = 0;
    double dayCarbohydrates = 0;

    for (Food food : user.getAteFoodsByDay()) {
      dayCalories += food.getCalories();
      dayProteins += food.getProteins();
      dayFats += food.getFats();
      dayCarbohydrates += food.getCarbohydrates();
    }

    StringBuilder stringBuilder = new StringBuilder(String.format("Твой дневной результат! %s\n\n",
        Emoji.TROPHY.getEmoji()));
    CalculateResult calculatedResult = user.getCalculatedResult();

    calculateDayResult("калории",
        Emoji.FORK, calculatedResult.getActivityCalories(), dayCalories, stringBuilder);
    calculateDayResult("белки",
        Emoji.MEAT, calculatedResult.getProteins(), dayProteins, stringBuilder);
    calculateDayResult("жиры",
        Emoji.NUT, calculatedResult.getFats(), dayFats, stringBuilder);
    calculateDayResult("углеводы",
        Emoji.RAMEN, calculatedResult.getCarbohydrates(), dayCarbohydrates, stringBuilder);

    return stringBuilder.toString();
  }

  private void calculateDayResult(String typeCalculate, Emoji emoji, double calculatedValue, double dayValue,
      StringBuilder stringBuilder) {

    double resultValue = calculatedValue - dayValue;
    if (resultValue <= 0) {
      stringBuilder.append(String.format(
          "Ты закрыл все %s %s %s.\n",
          typeCalculate,
          emoji.getEmoji(),
          Emoji.DONE_CHECK.getEmoji())
      );
    } else {
      stringBuilder.append(String.format(
          "Ты не закрыл все %s %s %s.\n",
          typeCalculate,
          emoji.getEmoji(),
          Emoji.X.getEmoji())
      );
    }
    stringBuilder.append(String.format(
        " Необходимо %.1f, Дневной результат %.1f, Разница %.1f\n\n",
        calculatedValue,
        dayValue,
        resultValue
    ));
  }
}
