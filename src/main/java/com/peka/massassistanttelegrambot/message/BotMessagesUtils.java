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

  public String CONFIG_FAT_PERCENTAGE_MESSAGE = """
      Включить или выключить процент подкожного жира при расчете калорий и БЖУ!
            
      Текущее значение - %s %s
      """;

  public String CONFIG_PROTEINS_MESSAGE = """
      Изменить значение количества белка на кг массы тела!
      
       Текущее значение - %s %s
       
       Чтобы изменить значение нужно отправить сообщение!
       Пример - 2.0
      """;

  public String CONFIG_FATS_MESSAGE = """
      Изменить значение количества жиров на кг массы тела!
      
       Текущее значение - %s %s
       
       Чтобы изменить значение нужно отправить сообщение!
       Пример - 2.0
      """;

  public String CONFIG_LOCATION_MESSAGE = """
      Новое значение тайм зоны установленно! %s %s
      """;

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

  public String CALCULATE_FATS_MESSAGE = """
      [Введи свой процент жира!](https://cs10.pikabu.ru/post_img/big/2020/04/07/5/1586243325150517599.png)
      """;

  public String CALCULATE_TYPE_MESSAGE = """
      Выбери свою цель по подсчету калорий! %s
      То есть, поддержание текущего веса, уменьшение или увеличение!
      """;

  public String CALCULATE_RESULT_MESSAGE = """
      Твои данные! %s
            
      %s
      """;

  public String ERROR_NO_ATE_FOOD_FOR_DAY_RESULT_TASK = """
      Ты не заполнил ни одного блюда,
      я не могу посчитать твои результаты!
      Чтобы получилось в новом дне нажми на /add
      """;

  public String SCHEDULER_ENABLED_MESSAGE = """
      Ты включил отправку уведомлений о результате твоего питания за день. %s
      Сообщение приходит в 00:00 %s
      """;

  public String SCHEDULER_DISABLED_MESSAGE = """
      Ты выключил отправку уведомлений о результате твоего питания за день. %s
      """;

  public String ADDED_FOOD_MESSAGE = """
      Eда добавлена!
            
      %s %s
      %s %s
      %s %s
      %s %s
      """;

  public String CLEANED_FOOD_MESSAGE = """
      Вся еда удаленна!
      """;
}
