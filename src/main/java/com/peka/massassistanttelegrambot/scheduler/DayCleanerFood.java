package com.peka.massassistanttelegrambot.scheduler;

import com.peka.massassistanttelegrambot.model.CallbackMessages;
import com.peka.massassistanttelegrambot.model.Emoji;
import com.peka.massassistanttelegrambot.model.User;
import com.peka.massassistanttelegrambot.repo.RxMongodbUserRepository;
import com.peka.massassistanttelegrambot.utils.BotCommandsUtils;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.updateshandlers.SentCallback;

import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DayCleanerFood {

  private static final String TIME_ZONE_ID = "Asia/Yekaterinburg";
  private static final int DAY_PERIOD_IN_MINUTES = 24 * 60;

  private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
  private final RxMongodbUserRepository rxUserRepo;
  private final TelegramLongPollingBot bot;

  private Disposable disposable;

  @PostConstruct
  public void init() {
    log.info("Starting scheduled job for sending clear messages to users");
    // Get the current time in Asia/Yekaterinburg time zone
    ZoneId zoneId = ZoneId.of(TIME_ZONE_ID);
    ZonedDateTime now = ZonedDateTime.now(zoneId);

    // Calculate the delay until midnight
    ZonedDateTime midnight = now.with(LocalTime.MIDNIGHT);
    if (now.isAfter(midnight)) {
      midnight = midnight.plusDays(1); // If it's already past midnight, schedule for the next day
    }
    Duration initialDelay = Duration.between(now, midnight);

    scheduler.scheduleAtFixedRate(
        this::sendClearFoodMessageToUsers,
        initialDelay.toMinutes(),
        DAY_PERIOD_IN_MINUTES,
        TimeUnit.MINUTES
    );
  }

  @PreDestroy
  public void destroy() {
    disposable.dispose();
    scheduler.shutdown();
  }

  private void sendClearFoodMessageToUsers() {
    log.info("Started sending scheduled clear food messages for users");

    disposable = rxUserRepo.findAll()
        .doOnNext(value -> log.info(String.format("%s", Thread.currentThread().getName() + Thread.currentThread().getId())))
        .subscribeOn(Schedulers.computation())
        .observeOn(Schedulers.computation())
        .subscribe(this::sendMessageToUser);
  }

  private void sendMessageToUser(User user) {
    try {
      SendMessage messageToSend = createMessage(user);
      bot.executeAsync(messageToSend, new SentCallback<>() {
        @Override
        public void onResult(BotApiMethod<Message> method, Message response) {
          // Do something with the response, if needed
        }

        @Override
        public void onError(BotApiMethod<Message> method, TelegramApiRequestException exception) {
          log.error(
              String.format("Exception during send scheduled clear food message for user with id=%s", user.getId()),
              exception
          );
        }

        @Override
        public void onException(BotApiMethod<Message> method, Exception exception) {
          log.error(
              String.format("Exception during send scheduled clear food message for user with id=%s", user.getId()),
              exception
          );
        }
      });
    } catch (Exception exception) {
      log.error(
          String.format("Exception during send scheduled clear food message for user with id=%s", user.getId()),
          exception
      );
    }
  }

  private SendMessage createMessage(User user) {
    return SendMessage.builder()
        .replyMarkup(createInlineKeyboardMarkup())
        .chatId(user.getId())
        .text(String.format(
            BotCommandsUtils.SCHEDULED_CLEAR_FOOD_COMMAND_TEXT,
            Emoji.RAMEN.getEmoji())
        )
        .build();
  }

  private InlineKeyboardMarkup createInlineKeyboardMarkup() {
    InlineKeyboardButton yesButton = InlineKeyboardButton.builder()
        .text(CallbackMessages.CLEAR_FOOD_SCHEDULED.getData())
        .callbackData(CallbackMessages.CLEAR_FOOD_SCHEDULED.toString())
        .build();

    return InlineKeyboardMarkup.builder()
        .keyboard(Collections.singletonList(Collections.singletonList(yesButton)))
        .build();
  }
}
