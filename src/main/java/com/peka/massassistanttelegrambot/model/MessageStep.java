package com.peka.massassistanttelegrambot.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Getter
public enum MessageStep {

  START,
  CONFIG_ALL,
  CONFIG_FAT_PERCENTAGE,
  CONFIG_PROTEINS,
  CONFIG_FATS,
  CONFIG_LOCATION,
  CALCULATE_SEX,
  CALCULATE_AGE,
  CALCULATE_WEIGHT,
  CALCULATE_HEIGHT,
  CALCULATE_ACTIVITY,
  CALCULATE_FATS,
  CALCULATE_TYPE,
  CALCULATE_SUMMARY,
  CALCULATE_RESULT,
  CONFIG_DAY_RESULT_TASK,
  ADD_FOOD,
  CLEAR_FOOD
}
