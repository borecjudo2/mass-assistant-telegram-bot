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
      Привет, %s, приятно познакомиться!
      Я бот который считает твои калории и БЖУ.
      После чего анализирую результат и составляю тебе план питания с учетом твоих любимых и не любимых блюд, продуктов!
      Для того, чтобы начать мною пользоваться можешь посмотреть меню или уже сразу начать подсчет калорий /calculate !
      """;

  public String CALCULATE_COMMAND_TEXT = BotMessagesUtils.CALCULATE_SEX_MESSAGE;
}
