package com.peka.massassistanttelegrambot.command;

import com.peka.massassistanttelegrambot.message.BotMessagesUtils;
import lombok.experimental.UtilityClass;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@UtilityClass
public class BotCommandsUtils {

  public String START_COMMAND_TEXT = """
      Привет, %s, приятно познакомиться! %s
      Я бот который считает твои калории и БЖУ. %s
      Для того, чтобы начать мною пользоваться можешь посмотреть меню или уже сразу начать подсчет калорий /calculate !
      """;

  public String CONFIG_ALL_COMMAND_TEXT = """
      Ты можешь поменять значения по которым рассчитывается количество калорий и БЖУ! %s
            
      Учитывать процент подкожного жира - %s %s
      Значение количества белка на кг массы тела - %.1f %s
      Значение количества жиров на кг массы тела - %.1f %s
      """;
  public String CONFIG_PROTEINS_COMMAND_TEXT = """
      Ты можешь поменять значение по которому рассчитывается количество белков на кг массы тела!
      По умолчанию стоит значению - 1.5 %s
            
      Текущее значение - %s %s
      """;

  public String CONFIG_LOCATION_COMMAND_TEXT = """
      Если ты хочешь воспользоваться функцией трекера калорий,
      то отправь свою текущую локацию %s и я высчитаю твою тайм зону
      и буду контролировать твои калорий 24 часа и посылать в 00:00 твои результаты за день.
            
      P.S. Обещаю я сохраню только тайм зону и все (например Europe/London).
      Мне не интересно где ты живешь!)
      """;

  public String SCHEDULER_COMMAND_TEXT = """
      Ты можешь включить или выключить подсчет и уведомление о результате твоего питания за день %s
      Текущее значение %s
      """;

  public String ADD_FOOD_COMMAND_TEXT = """
      Для того, чтобы добавить еду которую ты съел
      нужно прислать сообщение в таком формате:
      Белки %s
      Жиры %s
      Углеводы %s
            
      Пример:
      50.2
      6
      123
            
      А я потом посчитаю получившиеся калории!
      """;

  public String CLEAR_FOOD_COMMAND_TEXT = """
      Удалить всю твою съеденную еду? %s
      """;

  public String CALCULATE_COMMAND_TEXT = BotMessagesUtils.CALCULATE_SEX_MESSAGE;
}
