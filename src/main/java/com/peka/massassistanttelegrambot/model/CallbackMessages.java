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

  CALLBACK_SPLITTER("//"),
  CALCULATE("Подсчитать калории и БЖУ " + Emoji.DATA.getEmoji() + Emoji.SPAGHETTI.getEmoji()),
  CALCULATE_AGAIN("Подсчитать с новыми данными " + Emoji.DATA.getEmoji() + Emoji.SPAGHETTI.getEmoji()),
  ENABLE_DAY_RESULT_TASK("Включить " + Emoji.DONE_CHECK.getEmoji()),
  DISABLE_DAY_RESULT_TASK("Выключить " + Emoji.X.getEmoji()),
  CHANGE_CONFIG("Изменить значения"),
  CONFIG_FAT_PERCENTAGE("Вкл/Выкл процент жира " + Emoji.NUT.getEmoji()),
  ENABLE_CONFIG_FAT_PERCENTAGE("Вкл процент жира " + Emoji.DONE_CHECK.getEmoji()),
  DISABLE_CONFIG_FAT_PERCENTAGE("Выкл процент жира " + Emoji.X.getEmoji()),
  CONFIG_PROTEINS("Изменить количество белка " + Emoji.MEAT.getEmoji()),
  CONFIG_FATS("Изменить количество жиров " + Emoji.NUT.getEmoji()),
  KEEP_CONFIG("Оставить так как есть"),
  FOOD_ADD_TO_LIKED("Добавить в избранное " + Emoji.BLACK_HEART.getEmoji()),
  FOOD_REMOVE_FROM_LIKED("Удалить из избранных " + Emoji.RED_HEART.getEmoji()),
  ADD_TO_EATEN_FOOD("Добавить в съеденное за день " + Emoji.FORK.getEmoji()),
  OPEN_FOOD_DETAILS(""),
  CLEAR_FOOD("Да"),
  CLEAR_FOOD_SCHEDULED("Да");

  private final String data;
}
