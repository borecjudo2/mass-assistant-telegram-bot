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
public enum ExceptionMessage {

  ERROR_FOUND_FOOD("""
      Я не могу добавить эту еду в избранные!
      Я могу добавить в избранные еду, которую ты добавил сегодня!
      Чтобы добавить еду в избранную съешь ее сегодня и добавь в список съеденного за день через /add ,
      а потом можешь добавить в избранные!
      """),

  ERROR_DELETED_FOOD("""
      Еда была удаленна!
      """);

  private final String text;
}
