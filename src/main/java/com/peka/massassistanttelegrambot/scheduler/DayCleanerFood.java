package com.peka.massassistanttelegrambot.scheduler;

import com.peka.massassistanttelegrambot.model.CallbackMessages;
import com.peka.massassistanttelegrambot.model.Emoji;
import com.peka.massassistanttelegrambot.model.LatestMessage;
import com.peka.massassistanttelegrambot.model.MessageStep;
import com.peka.massassistanttelegrambot.model.User;
import com.peka.massassistanttelegrambot.repo.MongodbUserRepository;
import com.peka.massassistanttelegrambot.utils.BotCommandsUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

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
  private final MongodbUserRepository userRepository;
  private final TelegramLongPollingBot bot;

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
    scheduler.shutdown();
  }

  private void sendClearFoodMessageToUsers() {
    log.info("Started sending scheduled clear food messages for users");
    userRepository.findAll().forEach(this::sendMessageToUser);
    log.info("Finished sending scheduled clear food messages for users");
  }

  private void sendMessageToUser(User user) {
    try {
      User updatedUser = updateUserCurrentLatestMessage(user);
      SendMessage messageToSend = createMessage(updatedUser);
      Message executedMessage = bot.execute(messageToSend);

      user.getLatestMessage().setMessageId(executedMessage.getMessageId());
      userRepository.save(user);
    } catch (Exception exception) {
      log.error(String.format("Exception during send scheduled clear food message for user with id=%s", user.getId()),
          exception);
    }
  }

  private User updateUserCurrentLatestMessage(User user) {
    LatestMessage latestMessage = LatestMessage.builder()
        .chatId(user.getId())
        .messageStep(MessageStep.CLEAR_FOOD)
        .build();

    user.setLatestMessage(latestMessage);

    return user;
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
        .text(CallbackMessages.CLEAR_FOOD.getData())
        .callbackData(CallbackMessages.CLEAR_FOOD.toString())
        .build();

    return InlineKeyboardMarkup.builder()
        .keyboard(Collections.singletonList(Collections.singletonList(yesButton)))
        .build();
  }
}
