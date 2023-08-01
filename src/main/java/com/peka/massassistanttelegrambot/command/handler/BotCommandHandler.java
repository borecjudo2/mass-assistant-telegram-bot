package com.peka.massassistanttelegrambot.command.handler;

import com.peka.massassistanttelegrambot.exception.TelegramException;
import com.peka.massassistanttelegrambot.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Slf4j
public abstract class BotCommandHandler {

  @Autowired
  private TelegramLongPollingBot bot;

  public boolean isMyCommand(Update update) {
    return update.hasMessage() && getCommandName().equals(update.getMessage().getText());
  }

  public User handle(Update update, User user) {
    try {
      user = fillUserData(update, user);
      SendMessage messageToSend = createMessage(update, user);
      Message executedMessage = bot.execute(messageToSend);

      if (user != null && user.getLatestMessage() != null) {
        user.getLatestMessage().setMessageId(executedMessage.getMessageId());
      }

      return user;
    } catch (TelegramApiException exception) {
      log.error(String.format("Exception during execute send message for command=%s", getCommandName()), exception);
      throw new TelegramException(update, exception);
    }
  }

  protected abstract String getCommandName();

  protected abstract User fillUserData(Update update, User user);

  protected abstract SendMessage createMessage(Update update, User user);
}
