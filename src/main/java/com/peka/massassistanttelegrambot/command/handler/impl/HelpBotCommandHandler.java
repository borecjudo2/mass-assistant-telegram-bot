package com.peka.massassistanttelegrambot.command.handler.impl;

import com.peka.massassistanttelegrambot.command.handler.BotCommandHandler;
import com.peka.massassistanttelegrambot.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class HelpBotCommandHandler extends BotCommandHandler {

  private static final String HELP = "/help";

  @Override
  protected String getCommandName() {
    return HELP;
  }

  @Override
  protected User fillUserData(Update update, User user) {
    return user;
  }

  @Override
  protected SendMessage createMessage(Update update, User user) {
    return SendMessage.builder()
        .chatId(update.getMessage().getChatId())
        .text("Я считаю калории по этой [формуле](https://clck.ru/35AVgL), если выбран дефицит, то от этого значения " +
            "минус 15%, если поддержка, то так и остается и если профицит, то плюс 15%.\n\n" +
            "БЖУ считаются так:\n" +
            "Белки - вес умножить 1.5\n" +
            "Жиры - вес умножить 1\n" +
            "Углеводы - все калории минус калории от белков и жиров\n\n" +
            "Какие-нибудь вопросы? Пиши моему папе - @borecjudo"
        )
        .parseMode("Markdown")
        .build();
  }
}
