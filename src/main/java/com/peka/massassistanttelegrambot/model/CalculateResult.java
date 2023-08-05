package com.peka.massassistanttelegrambot.model;

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
    String text = """
        Тебе нужно %.1f к. %s
                
        Из них:
                
        %s Белки - %.1f гр.
        %s Жиры - %.1f гр.
        %s Углеводы - %.1f гр.
        """;

    return String.format(
        text,
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
