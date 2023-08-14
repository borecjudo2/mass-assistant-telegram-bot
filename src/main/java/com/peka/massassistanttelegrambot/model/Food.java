package com.peka.massassistanttelegrambot.model;

import com.peka.massassistanttelegrambot.message.BotMessagesUtils;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Data
@Builder
public class Food {

  private String name;

  private double calories;

  private double proteins;

  private double fats;

  private double carbohydrates;

  @Override
  public String toString() {
    return String.format(
        BotMessagesUtils.ADDED_FOOD_MESSAGE,
        name,
        calories,
        Emoji.FORK.getEmoji(),
        proteins,
        Emoji.MEAT.getEmoji(),
        fats,
        Emoji.NUT.getEmoji(),
        carbohydrates,
        Emoji.RAMEN.getEmoji()
    );
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Food food = (Food) o;

    return Objects.equals(name, food.name);
  }

  @Override
  public int hashCode() {
    return name != null ? name.hashCode() : 0;
  }
}
