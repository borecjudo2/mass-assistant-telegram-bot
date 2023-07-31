package com.peka.massassistanttelegrambot.message;

import lombok.experimental.UtilityClass;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@UtilityClass
public class BotMessagesUtils {

  public String CALCULATE_SEX_MESSAGE = """
      Выбери свой пол!
      """;

  public String CALCULATE_AGE_MESSAGE = """
      Введи свой возраст!
      Пример: 33
      """;

  public String CALCULATE_WEIGHT_MESSAGE = """
      Введи свой вес!
      Пример: 73.5
      """;

  public String CALCULATE_HEIGHT_MESSAGE = """
      Введи свой рост!
      Пример: 183.7
      """;

  public String CALCULATE_ACTIVITY_MESSAGE = """
      Выбери свою активность!
            
      1,2 - Физическая нагрузка отсутствует или минимальная
      1,38 - Тренировки средней тяжести 3 раза в неделю
      1,46 - Тренировки средней тяжести 5 раз в неделю
      1,55 - Интенсивные тренировки 5 раз в неделю
      1,64 - Тренировки каждый день
      1,73 - Интенсивные тренировки каждый день или по 2 раза в день
      1,9 - Ежедневная нагрузка + физическая работа
      """;

  public String CALCULATE_TYPE_MESSAGE = """
      Выбери свою цель!
      """;

  public String CALCULATE_RESULT_MESSAGE = """
      Твои данные!
            
      %s
      """;
}
