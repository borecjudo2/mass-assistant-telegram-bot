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

  DEFICIT("Дефицит\n" + Emoji.CALC_DOWN.getEmoji(), 0.85),
  NORMAL("Поддержание\n" + Emoji.CALC_NORMAL.getEmoji(), 1),
  PROFIT("Профицит\n" + Emoji.CALC_UP.getEmoji(), 1.15);

  private final String data;
  private final double value;
}
