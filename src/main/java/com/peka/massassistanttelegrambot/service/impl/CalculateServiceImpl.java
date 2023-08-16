package com.peka.massassistanttelegrambot.service.impl;

import com.peka.massassistanttelegrambot.model.CalculateResult;
import com.peka.massassistanttelegrambot.model.Emoji;
import com.peka.massassistanttelegrambot.model.Food;
import com.peka.massassistanttelegrambot.model.User;
import com.peka.massassistanttelegrambot.service.CalculateService;
import com.peka.massassistanttelegrambot.utils.BotMessagesUtils;
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

    double userWeight = user.getWeight();
    if (user.isFatPercentageEnabled()) {
      double fatPercentage = 1 - (user.getFatPercentage() / 100.0);
      userWeight = user.getWeight() * fatPercentage;
    }
    double proteins = user.getProteinsValue() * userWeight;
    double fats = user.getFatsValue() * userWeight;
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

    StringBuilder stringBuilder = new StringBuilder(String.format(BotMessagesUtils.DAY_RESULT,
        Emoji.TROPHY.getEmoji()));
    CalculateResult calculatedResult = user.getCalculatedResult();

    calculateDayResult(BotMessagesUtils.CALORIES,
        Emoji.FORK, calculatedResult.getActivityCalories(), dayCalories, stringBuilder);
    calculateDayResult(BotMessagesUtils.PROTEINS,
        Emoji.MEAT, calculatedResult.getProteins(), dayProteins, stringBuilder);
    calculateDayResult(BotMessagesUtils.FATS,
        Emoji.NUT, calculatedResult.getFats(), dayFats, stringBuilder);
    calculateDayResult(BotMessagesUtils.CARBS,
        Emoji.RAMEN, calculatedResult.getCarbohydrates(), dayCarbohydrates, stringBuilder);

    return stringBuilder.toString();
  }

  @Override
  public Food calcylateCaloriesForFood(Food food) {
    double calories = food.getProteins() * 4.2 + food.getFats() * 9.29 + food.getCarbohydrates() * 4.2;
    food.setCalories(calories);
    return food;
  }

  private void calculateDayResult(String typeCalculate, Emoji emoji, double calculatedValue, double dayValue,
      StringBuilder stringBuilder) {

    double resultValue = calculatedValue - dayValue;
    if (resultValue <= 0) {
      stringBuilder.append(String.format(
          BotMessagesUtils.CLOSED_ALL,
          typeCalculate,
          emoji.getEmoji(),
          Emoji.DONE_CHECK.getEmoji())
      );
    } else {
      stringBuilder.append(String.format(
          BotMessagesUtils.NOT_CLOSED_ALL,
          typeCalculate,
          emoji.getEmoji(),
          Emoji.X.getEmoji())
      );
    }
    stringBuilder.append(String.format(
        BotMessagesUtils.DAY_RESULT_DETAILS,
        calculatedValue,
        dayValue,
        resultValue
    ));
  }
}
