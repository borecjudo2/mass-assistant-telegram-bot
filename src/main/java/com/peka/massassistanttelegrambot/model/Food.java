package com.peka.massassistanttelegrambot.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Document("foods")
@Data
@Builder
public class Food {

  @Id
  private String name;

  private double calories;

  private double proteins;

  private double fats;

  private double carbohydrates;
}
