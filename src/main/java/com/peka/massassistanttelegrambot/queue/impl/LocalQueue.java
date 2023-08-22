package com.peka.massassistanttelegrambot.queue.impl;

import com.peka.massassistanttelegrambot.queue.BotQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.ref.SoftReference;
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

  private static final int QUEUE_CAPACITY = 1000;

  private SoftReference<BlockingQueue<Update>> queueSoftReference =
      new SoftReference<>(new LinkedBlockingQueue<>(QUEUE_CAPACITY));

  @Override
  public void putUpdate(Update update) {
    try {
      BlockingQueue<Update> queue = queueSoftReference.get();

      if (queue == null) {
        createNewSoftQueue();
        putUpdate(update);
      } else {
        queue.put(update);
      }
    } catch (InterruptedException exception) {
      throw new RuntimeException("Exception when putting an update to the queue", exception);
    }
  }

  @Override
  public Update takeUpdate() {
    try {
      BlockingQueue<Update> queue = queueSoftReference.get();

      if (queue == null) {
        createNewSoftQueue();
        return takeUpdate();
      } else {
        return queue.take();
      }
    } catch (InterruptedException exception) {
      throw new RuntimeException("Exception when receiving an update from the queue", exception);
    }
  }

  private void createNewSoftQueue() {
    queueSoftReference = new SoftReference<>(new LinkedBlockingQueue<>(QUEUE_CAPACITY));
  }
}
