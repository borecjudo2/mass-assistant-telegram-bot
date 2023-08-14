package com.peka.massassistanttelegrambot.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

/**
 * DESCRIPTION
 *
 * @author Vladislav Karpeka
 * @version 1.0.0
 */
@Document("users")
@Data
@Builder
public class User {

  @Id
  private Long id;

  private String username;

  private int age;

  private double weight;

  private double height;

  private Sex sex;

  private double activity;

  private boolean isFatPercentageEnabled;

  private int fatPercentage;

  private double proteinsValue;

  private double fatsValue;

  private String timeZone;

  private CalculateType calculateType;

  private LatestMessage latestMessage;

  private boolean isResultTaskEnabled;

  private CalculateResult calculatedResult;

  private List<Food> ateFoodsByDay;

  private Set<Food> likedFoods;

  @Override
  public String toString() {
    if (isFatPercentageEnabled) {
      String summary = """
        %s Твой пол - %s
        %s Твой возраст - %s
        %s Твой вес - %s
        %s Твой рост - %s
        %s Твоя активность - %s
        %s Твой процент жира - %s
        %s Твоя цель - %s
        """;

      return String.format(
          summary,
          Emoji.MAN_MAGE.getEmoji() + Emoji.WOMAN_MAGE.getEmoji(),
          sex.getValue(),
          Emoji.UNDERAGE.getEmoji(),
          age,
          Emoji.SCALES.getEmoji(),
          weight,
          Emoji.RULER.getEmoji(),
          height,
          Emoji.NONE_ACTIVITY.getEmoji(),
          activity,
          Emoji.NUT.getEmoji(),
          fatPercentage,
          Emoji.TROPHY.getEmoji(),
          calculateType.getData()
      );
    }

    String summary = """
        %s Твой пол - %s
        %s Твой возраст - %s
        %s Твой вес - %s
        %s Твой рост - %s
        %s Твоя активность - %s
        %s Твоя цель - %s
        """;

    return String.format(
        summary,
        Emoji.MAN_MAGE.getEmoji() + Emoji.WOMAN_MAGE.getEmoji(),
        sex.getValue(),
        Emoji.UNDERAGE.getEmoji(),
        age,
        Emoji.SCALES.getEmoji(),
        weight,
        Emoji.RULER.getEmoji(),
        height,
        Emoji.NONE_ACTIVITY.getEmoji(),
        activity,
        Emoji.TROPHY.getEmoji(),
        calculateType.getData()
    );
  }
}
