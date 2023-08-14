package com.peka.massassistanttelegrambot.message.handler.impl;

import com.peka.massassistanttelegrambot.exception.TelegramException;
import com.peka.massassistanttelegrambot.message.BotMessagesUtils;
import com.peka.massassistanttelegrambot.message.handler.BotMessageHandler;
import com.peka.massassistanttelegrambot.model.Emoji;
import com.peka.massassistanttelegrambot.model.MessageStep;
import com.peka.massassistanttelegrambot.model.User;
import lombok.RequiredArgsConstructor;
import net.iakovlev.timeshape.TimeZoneEngine;
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
    if (!update.getMessage().hasLocation()) {
      throw new TelegramException("Ты не отправил локацию!(", update, true);
    }

    ZoneId zoneId = timeZoneEngine.query(
            update.getMessage().getLocation().getLatitude(),
            update.getMessage().getLocation().getLongitude()
        )
        .orElseThrow(() -> new TelegramException("Ты не отправил локацию!(", update, true));

    user.setTimeZone(zoneId.getId());
    return user;
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
