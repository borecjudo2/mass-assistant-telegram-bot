package com.peka.massassistanttelegrambot.scheduler;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
public interface DayResultSchedulerService {

  void schedule(long chatId, String timeZone);

  boolean isExistTask(long chatId);

  void deleteSchedule(long chatId);
}
