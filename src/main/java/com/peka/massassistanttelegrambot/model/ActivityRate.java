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

  NONE(1.2, "1.2 " + Emoji.NONE_ACTIVITY.getEmoji()),
  LOWER(1.38, "1.38 " + Emoji.LOW.getEmoji()),
  MID_LOWER(1.46, "1.46 " + Emoji.MID_LOW.getEmoji()),
  MID_UPPER(1.55, "1.55 " + Emoji.MID_UP.getEmoji()),
  HIGH_LOWER(1.64, "1.64 " + Emoji.UP_LOW.getEmoji()),
  HIGH_UPPER(1.73, "1.73 " + Emoji.UP_UP.getEmoji()),
  HYPER(1.9, "1.9 " + Emoji.HYPER.getEmoji());

  private final double rate;
  private final String text;
}
