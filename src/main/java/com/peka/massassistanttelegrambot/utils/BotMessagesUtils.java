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
      Ты можешь включить или выключить учет процента подкожного жира при расчете калорий и питательных веществ!
              
      Сейчас эта функция %s %s
      """;

  public String CONFIG_PROTEINS_MESSAGE = """
      Измени количество белка на килограмм массы тела!
              
      Текущее значение - %s %s
          
      Для изменения значения отправь мне сообщение.
      Пример: 2.0
      """;

  public String CONFIG_FATS_MESSAGE = """
      Измени количество жиров на килограмм массы тела!
              
      Текущее значение - %s %s
          
      Для изменения значения отправь мне сообщение.
      Пример: 2.0
      """;

  public String CONFIG_LOCATION_MESSAGE = """
      Теперь новое значение тайм зоны установлено! %s %s
      """;

  public String CALCULATE_SEX_MESSAGE = """
      Укажи свой пол: %s %s
      """;

  public String CALCULATE_AGE_MESSAGE = """
      Укажи свой возраст: %s
      Например: 33
      """;

  public String CALCULATE_WEIGHT_MESSAGE = """
      Укажи свой вес: %s
      Например: 73.5
      """;

  public String CALCULATE_HEIGHT_MESSAGE = """
      Укажи свой рост: %s
      Например: 183.7
      """;

  public String CALCULATE_ACTIVITY_MESSAGE = """
      Выбери свой уровень активности:
          
      %s 1,2 - Физическая нагрузка отсутствует или минимальная
          
      %s 1,38 - Тренировки средней интенсивности 3 раза в неделю
          
      %s 1,46 - Тренировки средней интенсивности 5 раз в неделю
          
      %s 1,55 - Интенсивные тренировки 5 раз в неделю
          
      %s 1,64 - Тренировки каждый день
          
      %s 1,73 - Тренировки каждый день или по 2 раза в день
          
      %s 1,9 - Ежедневная интенсивная нагрузка плюс физическая работа
      """;

  public String CALCULATE_FATS_MESSAGE = """
      [Укажи свой процент жира!](https://cs10.pikabu.ru/post_img/big/2020/04/07/5/1586243325150517599.png)
      """;

  public String CALCULATE_TYPE_MESSAGE = """
      Выбери свою цель в подсчете калорий: %s
      То есть, ты хочешь поддерживать текущий вес, снизить его или увеличить!
      """;

  public String CALCULATE_RESULT_MESSAGE = """
      Вот твои данные! %s
          
      %s
      """;

  public String ERROR_NO_ATE_FOOD_FOR_DAY_RESULT_TASK = """
      Кажется, ты не добавил ни одного продукта.
      Я не могу рассчитать результаты без данных о питании.
      Чтобы начать, нажми на /add.
      """;

  public String SCHEDULER_ENABLED_MESSAGE = """
      Ты включил отправку уведомлений о результатах твоего питания за день. %s
      Ты будешь получать сообщение в 00:00 %s
      """;

  public String SCHEDULER_DISABLED_MESSAGE = """
      Ты выключил отправку уведомлений о результатах твоего питания за день. %s
      """;

  public String ADDED_FOOD_MESSAGE = """      
      %s
            
      %.1f %s
      %s %s
      %s %s
      %s %s
      """;

  public String CLEANED_FOOD_MESSAGE = """
      Все данные о съеденной еде удалены!
      """;

  public String LIST_LIKED_FOODS = """
      Вот список избранной еды:
      """;

  public String EMPTY_LIKED_FOODS = """
      Список избранной еды пока пуст!
      """;

  public String USER_NOT_LOGIN = "Чтобы начать, нажми /start. Ты ещё не зарегистрирован!";

  public String USER_RESULT_NOT_CALCULATED = "Чтобы узнать свою дневную норму, нажми /calculate!";

  public String USER_LOCATION_NOT_SET = "Чтобы я знал твою тайм зону, нажми /location!";

  public String ENABLED = "Включено";

  public String DISABLED = "Выключено";

  public String HELP_MESSAGE = """
      Я помогу тебе считать калории по этой [формуле](https://clck.ru/35AVgL). Если выбран дефицит, то к этому значению
      будет вычтено 15%. Если цель - поддерживать вес, то значение останется неизменным. Если цель - набрать вес, то добавится 15%.
          
      Расчет БЖУ (белки, жиры, углеводы):
      - Белки: вес умножается на 1.5 (это значение можно настроить через /config)
      - Жиры: вес умножается на 1 (это значение можно настроить через /config)
      - Углеводы: общие калории минус калории от белков и жиров
          
      Есть вопросы? Пиши моему создателю - @borecjudo.
      """;

  public String EAT_ADDED = "Еда успешно добавлена!\n\n";

  public String ERROR_SIZE_LIMIT_LIKED_FOOD =
      "К сожалению, ты не можешь добавить больше в избранное. Максимум 10 позиций!";

  public String ERROR_VALIDATION_FOOD_NAME = "Имя продукта не может иметь такого значения!";

  public String ERROR_VALIDATION_FOOD_PROTEIN = "Значение белка не может быть таким!";

  public String ERROR_VALIDATION_FOOD_FATS = "Значение жиров не может быть таким!";

  public String ERROR_VALIDATION_FOOD_CARB = "Значение углеводов не может быть таким!";

  public String ERROR_FORMAT_FOOD = "Сообщение о еде отправлено в неправильном формате.";

  public String SOMETHING_WENT_WRONG = "Что-то пошло не так";

  public String ERROR_VALIDATION_AGE = "Возникла ошибка при проверке возраста! Пожалуйста, повторите ещё раз.";

  public String ERROR_VALIDATION_FATS_PERCENTAGE = "Возникла ошибка при проверке процента жира! Пожалуйста, повторите ещё раз.";

  public String ERROR_VALIDATION_HEIGHT = "Возникла ошибка при проверке роста! Пожалуйста, повторите ещё раз.";

  public String ERROR_VALIDATION_WEIGHT = "Возникла ошибка при проверке веса! Пожалуйста, повторите ещё раз.";

  public String ERROR_VALIDATION_CONFIG_FAT_VALUE = "Возникла ошибка при проверке количества жиров! Пожалуйста, повторите ещё раз.";

  public String ERROR_VALIDATION_CONFIG_PROTEIN_VALUE = "Возникла ошибка при проверке количества белка! Пожалуйста, повторите ещё раз.";

  public String ERROR_VALIDATION_LOCATION = """
      Возникла ошибка при проверке локации!
      Локация не найдена!
      Пожалуйста, повторите ещё раз!
      """;

  public String ERROR_LOCATION_NOT_SEND = "Ты не отправил локацию!";

  public String UNEXPECTED_ERROR = "Что-то пошло не так. Пожалуйста, перестаньте баловаться!";

  public String CALCULATE_RESULT = """
      Тебе нужно %.1f калорий. %s
          
      Из них:
          
      %s Белки - %.1f гр.
      %s Жиры - %.1f гр.
      %s Углеводы - %.1f гр.
      """;

  public String USER_WITH_PERCENTAGE_FAT = """
      %s Твой пол: %s
      %s Твой возраст: %s
      %s Твой вес: %s
      %s Твой рост: %s
      %s Твоя активность: %s
      %s Твой процент жира: %s
      %s Твоя цель: %s
      """;

  public String USER = """
      %s Твой пол: %s
      %s Твой возраст: %s
      %s Твой вес: %s
      %s Твой рост: %s
      %s Твоя активность: %s
      %s Твоя цель: %s
      """;

  public String DAY_RESULT = "Вот твои результаты за день! %s\n\n";

  public String CALORIES = "калории";

  public String PROTEINS = "белки";

  public String FATS = "жиры";

  public String CARBS = "углеводы";

  public String CLOSED_ALL_FOOD_GAOL = "Ты закрыл все %s %s %s.\n";

  public String NOT_CLOSED_ALL_FOOD_GAOL = "Ты не закрыл все %s %s %s.\n";

  public String DAY_RESULT_DETAILS = " Необходимо %.1f, Дневной результат %.1f, Разница %.1f\n\n";
}
