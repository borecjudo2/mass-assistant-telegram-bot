package com.peka.massassistanttelegrambot.local.init;

import com.peka.massassistanttelegrambot.model.CalculateResult;
import com.peka.massassistanttelegrambot.model.CalculateType;
import com.peka.massassistanttelegrambot.model.Food;
import com.peka.massassistanttelegrambot.model.LatestMessage;
import com.peka.massassistanttelegrambot.model.MessageStep;
import com.peka.massassistanttelegrambot.model.Sex;
import com.peka.massassistanttelegrambot.model.User;
import com.peka.massassistanttelegrambot.repo.MongodbUserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Service
@Profile("local")
@RequiredArgsConstructor
public class LocalDbMigration {

  private final MongodbUserRepository userRepository;

  @PostConstruct
  public void init() {
    User user = User.builder()
        .id(460797262L)
        .username("borecjudo")
        .age(33)
        .weight(90)
        .height(190)
        .sex(Sex.MAN)
        .activity(1.38)
        .isFatPercentageEnabled(true)
        .fatPercentage(20)
        .proteinsValue(2)
        .fatsValue(1)
        .timeZone("Europe/Minsk")
        .calculateType(CalculateType.PROFIT)
        .isResultTaskEnabled(true)
        .latestMessage(LatestMessage.builder()
            .chatId(460797262L)
            .messageStep(MessageStep.START)
            .build())
        .calculatedResult(CalculateResult.builder()
            .activityCalories(3000)
            .proteins(150)
            .fats(50)
            .carbohydrates(300)
            .build())
        .ateFoodsByDay(List.of(
            Food.builder()
                .name("Expanenta")
                .calories(100)
                .proteins(30)
                .fats(0)
                .carbohydrates(5)
                .build(),
            Food.builder()
                .name("Овсянка")
                .calories(500)
                .proteins(16)
                .fats(14)
                .carbohydrates(100)
                .build()
        ))
        .likedFoods(Set.of(
            Food.builder()
                .name("Expanenta1")
                .calories(100)
                .proteins(30)
                .fats(0)
                .carbohydrates(5)
                .build(),
            Food.builder()
                .name("Овсянка1")
                .calories(500)
                .proteins(16)
                .fats(14)
                .carbohydrates(100)
                .build(),
            Food.builder()
                .name("Expanenta2")
                .calories(100)
                .proteins(30)
                .fats(0)
                .carbohydrates(5)
                .build(),
            Food.builder()
                .name("Овсянка2")
                .calories(500)
                .proteins(16)
                .fats(14)
                .carbohydrates(100)
                .build(), Food.builder()
                .name("Expanenta3")
                .calories(100)
                .proteins(30)
                .fats(0)
                .carbohydrates(5)
                .build(),
            Food.builder()
                .name("Овсянка3")
                .calories(500)
                .proteins(16)
                .fats(14)
                .carbohydrates(100)
                .build(), Food.builder()
                .name("Expanenta4")
                .calories(100)
                .proteins(30)
                .fats(0)
                .carbohydrates(5)
                .build(),
            Food.builder()
                .name("Овсянка4")
                .calories(500)
                .proteins(16)
                .fats(14)
                .carbohydrates(100)
                .build(), Food.builder()
                .name("Expanenta5")
                .calories(100)
                .proteins(30)
                .fats(0)
                .carbohydrates(5)
                .build()
        ))
        .build();

    userRepository.save(user);
  }
}
