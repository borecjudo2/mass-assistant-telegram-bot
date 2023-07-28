package com.peka.massassistanttelegrambot.service;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
public interface BotService {

  void processUpdate(Update update);
}
