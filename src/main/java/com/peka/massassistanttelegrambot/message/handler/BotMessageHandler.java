package com.peka.massassistanttelegrambot.message.handler;

import com.peka.massassistanttelegrambot.exception.TelegramException;
import com.peka.massassistanttelegrambot.model.LatestMessage;
import com.peka.massassistanttelegrambot.model.MessageStep;
import com.peka.massassistanttelegrambot.model.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
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
public abstract class BotMessageHandler {

  @Autowired
  @Getter
  private TelegramLongPollingBot bot;

  public boolean isMyMessage(User user, Update update) {
    if (user == null) {
      throw new TelegramException("Нажми на /start ты не зарегистрирован!", update);
    }

    return getMessageStep().equals(user.getLatestMessage().getMessageStep());
  }

  public User handle(Update update, User user) {
    if (!isNeededMessageType(update)) {
      throw new TelegramException("Так хватит тут баловаться!", update);
    }

    try {
      user = fillUserData(update, user);

      DeleteMessage messageToDelete = createDeleteMessage(user);
      bot.execute(messageToDelete);

      SendMessage messageToSend = createNextMessage(update, user);
      Message executedMessage = bot.execute(messageToSend);

      user.getLatestMessage().setMessageId(executedMessage.getMessageId());
      user.getLatestMessage().setMessageStep(getNextMessageStep(user));

      return user;
    } catch (Exception exception) {
      log.error(String.format("Exception during execute send message for message=%s", getMessageStep()), exception);
      throw new TelegramException(update, exception);
    }
  }

  private DeleteMessage createDeleteMessage(User user) {
    LatestMessage message = user.getLatestMessage();

    return DeleteMessage.builder()
        .chatId(message.getChatId())
        .messageId(message.getMessageId())
        .build();
  }

  protected abstract boolean isNeededMessageType(Update update);

  protected abstract MessageStep getMessageStep();

  protected abstract MessageStep getNextMessageStep(User user);

  protected abstract User fillUserData(Update update, User user) throws TelegramApiException;

  protected abstract SendMessage createNextMessage(Update update, User user);
}
