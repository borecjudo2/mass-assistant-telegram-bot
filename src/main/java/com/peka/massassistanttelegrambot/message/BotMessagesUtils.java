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
      Выбери свой пол! %s %s
      """;

  public String CALCULATE_AGE_MESSAGE = """
      Введи свой возраст! %s
      Пример: 33
      """;

  public String CALCULATE_WEIGHT_MESSAGE = """
      Введи свой вес! %s
      Пример: 73.5
      """;

  public String CALCULATE_HEIGHT_MESSAGE = """
      Введи свой рост! %s
      Пример: 183.7
      """;

  public String CALCULATE_ACTIVITY_MESSAGE = """
      Выбери свою активность!
            
      %s 1,2 - Физическая нагрузка отсутствует или минимальная
            
      %s 1,38 - Тренировки средней тяжести 3 раза в неделю
            
      %s 1,46 - Тренировки средней тяжести 5 раз в неделю
            
      %s 1,55 - Интенсивные тренировки 5 раз в неделю
            
      %s 1,64 - Тренировки каждый день
            
      %s 1,73 - Тренировки каждый день или по 2 раза в день
            
      %s 1,9 - Ежедневная нагрузка + физическая работа
      """;

  public String CALCULATE_TYPE_MESSAGE = """
      Выбери свою цель по подсчету калорий! %s
      То есть, поддержание текущего веса, уменьшение или увеличение!
      """;

  public String CALCULATE_RESULT_MESSAGE = """
      Твои данные! %s
            
      %s
      """;
}
