package com.peka.massassistanttelegrambot.service.impl;

import com.peka.massassistanttelegrambot.command.handler.BotCommandHandler;
import com.peka.massassistanttelegrambot.exception.TelegramException;
import com.peka.massassistanttelegrambot.message.handler.BotMessageHandler;
import com.peka.massassistanttelegrambot.model.User;
import com.peka.massassistanttelegrambot.repo.UserRepository;
import com.peka.massassistanttelegrambot.service.BotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class BotServiceImpl implements BotService {

  private final TelegramLongPollingBot bot;

  private final UserRepository userRepository;
  private final List<BotCommandHandler> botCommandHandlers;
  private final List<BotMessageHandler> botMessageHandlers;

  @Override
  public void processUpdate(Update update) {
    User existingUser = update.hasMessage() ?
        userRepository.getUserByUsername(update.getMessage().getChat().getUserName()) :
        userRepository.getUserByUsername(update.getCallbackQuery().getMessage().getChat().getUserName());

    botCommandHandlers.stream()
        .filter(botCommandHandler -> botCommandHandler.isMyCommand(update))
        .findFirst()
        .ifPresentOrElse(botCommandHandler -> userRepository.saveUser(botCommandHandler.handle(update, existingUser)),
            handleMessages(existingUser, update));
  }

  private Runnable handleMessages(User existingUser, Update update) {
    return () -> botMessageHandlers.stream()
        .filter(botMessageHandler -> botMessageHandler.isMyMessage(existingUser, update))
        .findFirst()
        .ifPresentOrElse(botMessageHandler -> userRepository
            .saveUser(botMessageHandler.handle(update, existingUser)), handleOtherMessages(update));
  }

  private Runnable handleOtherMessages(Update update) {
    return () -> {
      try {
        SendMessage messageToSend = SendMessage.builder()
            .chatId(update.getMessage().getChatId())
            .text("Так хватит тут баловаться!")
            .build();
        bot.execute(messageToSend);
      } catch (TelegramApiException exception) {
        throw new TelegramException(update, exception);
      }
    };
  }
}
