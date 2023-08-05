package com.peka.massassistanttelegrambot.service.impl;

import com.peka.massassistanttelegrambot.model.CalculateResult;
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
}
