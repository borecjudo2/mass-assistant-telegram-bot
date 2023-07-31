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
        Твой пол - %s
        Твой возраст - %s
        Твой вес - %s
        Твой рост - %s
        Твоя активность - %s
        Твоя цель - %s
        """;
    return String.format(summary, sex.getValue(), age, weight, height, activity, calculateType.getData());
  }
}
