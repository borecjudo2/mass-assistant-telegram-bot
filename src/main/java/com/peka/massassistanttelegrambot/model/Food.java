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
public class Food {

  private double calories;

  private double proteins;

  private double fats;

  private double carbohydrates;
}
