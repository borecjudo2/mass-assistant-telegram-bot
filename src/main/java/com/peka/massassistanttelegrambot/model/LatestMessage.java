package com.peka.massassistanttelegrambot.model;

import lombok.Builder;
import lombok.Data;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Data
@Builder
public class LatestMessage {

  private long chatId;

  private int messageId;

  private MessageStep messageStep;
}
