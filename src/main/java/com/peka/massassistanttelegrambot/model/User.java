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

  private int weight;

  private int targetWeight;

  private int height;

  private Sex sex;

  private double activity;
}
