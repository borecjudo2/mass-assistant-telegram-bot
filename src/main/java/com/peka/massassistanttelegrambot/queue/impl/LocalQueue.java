package com.peka.massassistanttelegrambot.queue.impl;

import com.peka.massassistanttelegrambot.queue.BotQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class LocalQueue implements BotQueue {

  private final BlockingQueue<Update> updateQueue = new LinkedBlockingQueue<>(100);

  @Override
  public void putUpdate(Update update) {
    try {
      updateQueue.put(update);
    } catch (InterruptedException exception) {
      throw new RuntimeException(exception);
    }
  }

  @Override
  public Update takeUpdate() {
    try {
      return updateQueue.take();
    } catch (InterruptedException exception) {
      throw new RuntimeException(exception);
    }
  }
}
