package com.peka.massassistanttelegrambot.model;

import com.peka.massassistanttelegrambot.utils.BotMessagesUtils;
import lombok.Builder;
import lombok.Data;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Data
@Builder
public class CalculateResult {

  private double activityCalories;

  private double proteins;

  private double fats;

  private double carbohydrates;

  @Override
  public String toString() {
    return String.format(
        BotMessagesUtils.CALCULATE_RESULT,
        activityCalories,
        Emoji.FORK.getEmoji(),
        Emoji.MEAT.getEmoji(),
        proteins,
        Emoji.NUT.getEmoji(),
        fats,
        Emoji.RAMEN.getEmoji(),
        carbohydrates
    );
  }
}
