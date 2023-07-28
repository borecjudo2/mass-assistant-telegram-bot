package com.peka.massassistanttelegrambot.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Getter
public enum ActivityRate {

  HYPER(2),
  NORMAL(1),
  LOWER(0);

  private final int rate;

  public static ActivityRate getByRate(int rate) {
    return Arrays.stream(ActivityRate.values())
        .filter(activityRate -> activityRate.rate == rate)
        .findFirst()
        .orElseThrow();
  }
}
