package com.peka.massassistanttelegrambot.message.handler.impl;

import com.peka.massassistanttelegrambot.exception.TelegramException;
import com.peka.massassistanttelegrambot.message.handler.BotMessageHandler;
import com.peka.massassistanttelegrambot.model.Emoji;
import com.peka.massassistanttelegrambot.model.MessageStep;
import com.peka.massassistanttelegrambot.model.User;
import com.peka.massassistanttelegrambot.utils.BotMessagesUtils;
import lombok.RequiredArgsConstructor;
import net.iakovlev.timeshape.TimeZoneEngine;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.ZoneId;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Service
@ConditionalOnProperty(name = "service.location.enabled", havingValue = "true")
@RequiredArgsConstructor
public class ConfigLocationBotMessageHandler extends BotMessageHandler {

  private final TimeZoneEngine timeZoneEngine;

  @Override
  protected boolean isNeededMessageType(Update update) {
    return update.hasMessage();
  }

  @Override
  protected MessageStep getMessageStep() {
    return MessageStep.CONFIG_LOCATION;
  }

  @Override
  protected MessageStep getNextMessageStep(User user) {
    return MessageStep.START;
  }

  @Override
  protected User fillUserData(Update update, User user) {
    if (update.hasMessage() && update.getMessage().hasLocation()) {
      ZoneId zoneId = timeZoneEngine.query(
              update.getMessage().getLocation().getLatitude(),
              update.getMessage().getLocation().getLongitude()
          )
          .orElseThrow(() -> new TelegramException(BotMessagesUtils.ERROR_VALIDATION_LOCATION, update, true));

      user.setTimeZone(zoneId.getId());
      return user;
    }

    if (update.hasMessage() && update.getMessage().hasText()) {
      ZoneId zoneId = validateZoneIdByTextMessage(update);

      user.setTimeZone(zoneId.getId());
      return user;
    }

    throw new TelegramException(BotMessagesUtils.ERROR_LOCATION_NOT_SEND, update, true);
  }

  private ZoneId validateZoneIdByTextMessage(Update update) {
    try {
      return ZoneId.of(update.getMessage().getText());
    } catch (Exception e) {
      throw new TelegramException(BotMessagesUtils.ERROR_VALIDATION_LOCATION, update, true);
    }
  }

  @Override
  protected SendMessage createNextMessage(Update update, User user) {
    return SendMessage.builder()
        .chatId(update.getMessage().getChatId())
        .text(String.format(
            BotMessagesUtils.CONFIG_LOCATION_MESSAGE,
            user.getTimeZone(),
            Emoji.WORLD_MAP.getEmoji()
        ))
        .build();
  }
}
