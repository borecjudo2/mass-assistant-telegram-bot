package com.peka.massassistanttelegrambot.message.handler.custom;

import com.peka.massassistanttelegrambot.model.User;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
public interface BotCustomMessageHandler {

  boolean isMyMessage(Update update, User user);

  User handleMessage(Update update, User user);
}
