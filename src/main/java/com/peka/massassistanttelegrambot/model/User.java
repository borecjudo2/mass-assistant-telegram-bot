package com.peka.massassistanttelegrambot.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * DESCRIPTION
 *
 * @author Vladislav Karpeka
 * @version 1.0.0
 */
@Data
@Builder
public class User {

  private UUID id;

  private String username;

  private String firstName;

  private String lastName;

  private int age;

  private double weight;

  private double height;

  private Sex sex;

  private double activity;

  private CalculateType calculateType;

  private LatestMessage latestMessage;

  private String calculatedResult;

  @Override
  public String toString() {
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
