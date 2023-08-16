package com.peka.massassistanttelegrambot.service.impl;

import com.peka.massassistanttelegrambot.command.handler.BotCommandHandler;
import com.peka.massassistanttelegrambot.exception.TelegramException;
import com.peka.massassistanttelegrambot.message.handler.BotMessageHandler;
import com.peka.massassistanttelegrambot.message.handler.custom.BotCustomMessageHandler;
import com.peka.massassistanttelegrambot.model.User;
import com.peka.massassistanttelegrambot.repo.MongodbUserRepository;
import com.peka.massassistanttelegrambot.service.BotService;
import com.peka.massassistanttelegrambot.utils.BotMessagesUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
public class BotServiceImpl implements BotService {

  private final TelegramLongPollingBot bot;

  private final MongodbUserRepository userRepository;
  private final List<BotCommandHandler> botCommandHandlers;
  private final List<BotMessageHandler> botMessageHandlers;
  private final List<BotCustomMessageHandler> botBotCustomMessageHandlers;

  @Override
  public void processUpdate(Update update) {
    User existingUser = update.hasMessage() ?
        userRepository.findById(update.getMessage().getChatId()).orElse(null) :
        userRepository.findById(update.getCallbackQuery().getMessage().getChatId()).orElse(null);

    botCommandHandlers.stream()
        .filter(botCommandHandler -> botCommandHandler.isMyCommand(update))
        .findFirst()
        .ifPresentOrElse(botCommandHandler -> saveUser(botCommandHandler.handle(update, existingUser)),
            handleMessages(existingUser, update));
  }

  private Runnable handleMessages(User existingUser, Update update) {
    return () -> botMessageHandlers.stream()
        .filter(botMessageHandler -> botMessageHandler.isMyMessage(existingUser, update))
        .findFirst()
        .ifPresentOrElse(botMessageHandler -> saveUser(botMessageHandler.handle(update, existingUser)),
            handleCustomMessages(existingUser, update));
  }

  private Runnable handleCustomMessages(User existingUser, Update update) {
    return () -> botBotCustomMessageHandlers.stream()
        .filter(botMessageHandler -> botMessageHandler.isMyMessage(update, existingUser))
        .findFirst()
        .ifPresentOrElse(botMessageHandler ->
            saveUser(botMessageHandler.handleMessage(update, existingUser)), handleOtherMessages(update));
  }

  private Runnable handleOtherMessages(Update update) {
    return () -> {
      try {
        long chatId = update.hasCallbackQuery() ? update.getCallbackQuery().getMessage().getChatId() :
            update.getMessage().getChatId();

        SendMessage messageToSend = SendMessage.builder()
            .chatId(chatId)
            .text(BotMessagesUtils.UNEXPECTED_ERROR)
            .build();
        bot.execute(messageToSend);
      } catch (TelegramApiException exception) {
        throw new TelegramException(update, exception, false);
      }
    };
  }

  private void saveUser(User user) {
    userRepository.save(user);
    log.info(String.format("User saved! ChatID=%s Username=%s", user.getId(), user.getUsername()));
  }
}
