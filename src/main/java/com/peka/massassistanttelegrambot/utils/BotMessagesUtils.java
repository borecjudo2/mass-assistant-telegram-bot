package com.peka.massassistanttelegrambot.utils;

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
      %s
            
      %.1f %s
      %s %s
      %s %s
      %s %s
      """;

  public String CLEANED_FOOD_MESSAGE = """
      Вся еда удаленна!
      """;

  public String LIST_LIKED_FOODS = """
      Список избранной еды!
      """;

  public String EMPTY_LIKED_FOODS = """
      Список избранной еды - пуст!
      """;

  public String USER_NOT_LOGIN = "Нажми на /start ты не зарегистрирован!";

  public String USER_RESULT_NOT_CALCULATED = "Нажми на /calculate я не знаю твою дневную норму!";

  public String USER_LOCATION_NOT_SET = "Нажми на /location я не знаю твою тайм зону!";

  public String ENABLED = "Включено";

  public String DISABLED = "Выключено";

  public String HELP_MESSAGE = """
      Я считаю калории по этой [формуле](https://clck.ru/35AVgL), если выбран дефицит, то от этого значения
      минус 15%, если поддержка, то так и остается и если профицит, то плюс 15%.
      БЖУ считаются так:
      Белки - вес умножить 1.5, по умолчанию. Для настройки тыкни сюда /config
      Жиры - вес умножить 1
      Углеводы - все калории минус калории от белков и жиров
      Какие-нибудь вопросы? Пиши моему папе - @borecjudo"
      """;

  public String EAT_ADDED = "Еда добавлена!\n\n";

  public String ERROR_SIZE_LIMIT_LIKED_FOOD = "Ты не можешь больше добавить в избранные! Максимум 10 позиций!";

  public String ERROR_VALIDATION_FOOD_NAME = "Такого значения имени быть не может!";

  public String ERROR_VALIDATION_FOOD_PROTEIN = "Такого значения белка быть не может!";

  public String ERROR_VALIDATION_FOOD_FATS = "Такого значения жиров быть не может!";

  public String ERROR_VALIDATION_FOOD_CARB = "Такого значения углеводов быть не может!";

  public String ERROR_FORMAT_FOOD = "В неправильном формате отправлено сообщение об еде";

  public String SOMETHING_WENT_WRONG = "Что-то пошло не так";

  public String ERROR_VALIDATION_AGE = "Ошибка валидации возраста! Повторите еще раз!";

  public String ERROR_VALIDATION_FATS_PERCENTAGE = "Ошибка валидации процента жира! Повторите еще раз!";

  public String ERROR_VALIDATION_HEIGHT = "Ошибка валидации роста! Повторите еще раз!";

  public String ERROR_VALIDATION_WEIGHT = "Ошибка валидации веса! Повторите еще раз!";

  public String ERROR_VALIDATION_CONFIG_FAT_VALUE = "Ошибка валидации количества жиров! Повторите еще раз!";

  public String ERROR_VALIDATION_CONFIG_PROTEIN_VALUE = "Ошибка валидации количества белка! Повторите еще раз!";

  public String ERROR_LOCATION_NOT_FOUND = "Ты не отправил локацию!(";

  public String UNEXPECTED_ERROR = "Так, хватит тут баловаться!";

  public String CALCULATE_RESULT = """
      Тебе нужно %.1f к. %s
              
      Из них:
              
      %s Белки - %.1f гр.
      %s Жиры - %.1f гр.
      %s Углеводы - %.1f гр.
      """;

  public String USER_WITH_PERCENTAGE_FAT = """
      %s Твой пол - %s
      %s Твой возраст - %s
      %s Твой вес - %s
      %s Твой рост - %s
      %s Твоя активность - %s
      %s Твой процент жира - %s
      %s Твоя цель - %s
      """;

  public String USER = """
      %s Твой пол - %s
      %s Твой возраст - %s
      %s Твой вес - %s
      %s Твой рост - %s
      %s Твоя активность - %s
      %s Твоя цель - %s
      """;

  public String DAY_RESULT = "Твой дневной результат! %s\n\n";

  public String CALORIES = "калории";

  public String PROTEINS = "белки";

  public String FATS = "жиры";

  public String CARBS = "углеводы";

  public String CLOSED_ALL = "Ты закрыл все %s %s %s.\n";

  public String NOT_CLOSED_ALL = "Ты не закрыл все %s %s %s.\n";

  public String DAY_RESULT_DETAILS = " Необходимо %.1f, Дневной результат %.1f, Разница %.1f\n\n";
}
