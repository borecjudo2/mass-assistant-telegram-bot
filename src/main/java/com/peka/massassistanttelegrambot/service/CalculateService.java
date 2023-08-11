package com.peka.massassistanttelegrambot.service;

import com.peka.massassistanttelegrambot.model.CalculateResult;
import com.peka.massassistanttelegrambot.model.Food;
import com.peka.massassistanttelegrambot.model.User;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
public interface CalculateService {

  CalculateResult calculate(User user);

  String calculateDayResult(User user);

  Food calcylateCaloriesForFood(Food food);
}
