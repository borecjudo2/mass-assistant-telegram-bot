package com.peka.massassistanttelegrambot.model;

import com.peka.massassistanttelegrambot.utils.BotMessagesUtils;
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
      return String.format(
          BotMessagesUtils.USER_WITH_PERCENTAGE_FAT,
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

    return String.format(
        BotMessagesUtils.USER,
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
