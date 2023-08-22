package com.peka.massassistanttelegrambot.controller;

import com.peka.massassistanttelegrambot.exception.TelegramException;
import com.peka.massassistanttelegrambot.exception.TelegramExceptionHandler;
import com.peka.massassistanttelegrambot.queue.BotQueue;
import com.peka.massassistanttelegrambot.service.BotService;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class TelegramBotUpdateController {

  private final BotQueue queue;
  private final BotService service;
  private final TelegramExceptionHandler exceptionHandler;

  private Disposable disposable;

  @PostConstruct
  public void init() {
    Observable<Update> observable = Observable.create(emitter -> {
      while (!emitter.isDisposed()) {
        Update data = queue.takeUpdate();
        emitter.onNext(data);
      }
    });

    disposable = observable
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.computation())
        .subscribe(this::processUpdate);
  }

  private void processUpdate(Update update) {
    try {
      String threadName = String.format("%s-%s", Thread.currentThread().getName(), Thread.currentThread().getId());
      log.info("Just started processUpdate new iteration " + threadName);
      service.processUpdate(update);
    } catch (TelegramException exception) {
      exceptionHandler.handle(exception);
    } catch (Exception exception) {
      log.error(exception.getLocalizedMessage(), exception);
    }
  }

  @PreDestroy
  public void destroy() {
    log.info("Destroying");
    disposable.dispose();
  }
}
