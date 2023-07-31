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
public enum Sex {

  MAN("Мужчина", 5),
  WOMAN("Женщина", -161);

  private final String value;
  private final int data;

  public static Sex getByValue(String value) {
    return Arrays.stream(Sex.values())
        .filter(sex -> value.equalsIgnoreCase(sex.value))
        .findFirst()
        .orElseThrow();
  }
}
