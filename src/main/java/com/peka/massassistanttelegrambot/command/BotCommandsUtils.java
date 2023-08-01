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

  public String CALCULATE_COMMAND_TEXT = BotMessagesUtils.CALCULATE_SEX_MESSAGE;
}
