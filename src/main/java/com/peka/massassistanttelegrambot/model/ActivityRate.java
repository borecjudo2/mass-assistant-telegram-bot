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
public enum ActivityRate {

  NONE(1.2),
  LOWER(1.38),
  MID_LOWER(1.46),
  MID_UPPER(1.55),
  HIGH_LOWER(1.64),
  HIGH_UPPER(1.73),
  HYPER(1.9);

  private final double rate;
}
