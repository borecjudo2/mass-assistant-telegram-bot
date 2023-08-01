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
public enum CallbackMessages {

  CALCULATE("Подсчитать калории и БЖУ " + Emoji.DATA.getEmoji() + Emoji.SPAGHETTI.getEmoji()),
  CALCULATE_AGAIN("Подсчитать с новыми данными " + Emoji.DATA.getEmoji() + Emoji.SPAGHETTI.getEmoji());

  private final String data;
}
