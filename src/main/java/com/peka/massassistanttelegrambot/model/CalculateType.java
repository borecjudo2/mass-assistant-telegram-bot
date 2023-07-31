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
public enum CalculateType {

  DEFICIT("Дефицит", 0.85),
  NORMAL("Поддержание", 1),
  PROFIT("Профицит", 1.15);

  private final String data;
  private final double value;
}
