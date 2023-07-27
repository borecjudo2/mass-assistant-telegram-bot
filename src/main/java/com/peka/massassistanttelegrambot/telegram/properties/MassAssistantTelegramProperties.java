package com.peka.massassistanttelegrambot.telegram.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("telegram.bot")
public class MassAssistantTelegramProperties {

  private String name;

  private String apiKey;
}
