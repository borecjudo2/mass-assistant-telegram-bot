package com.peka.massassistanttelegrambot.queue;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
public interface BotQueue {

  void putUpdate(Update update);

  Update takeUpdate();
}
