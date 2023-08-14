package com.peka.massassistanttelegrambot.model;

import com.vdurmont.emoji.EmojiParser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Getter
public enum Emoji {

  WAVE(EmojiParser.parseToUnicode(":wave:")),
  RED_HEART(EmojiParser.parseToUnicode(":heart:")),
  BLACK_HEART(EmojiParser.parseToUnicode(":black_heart:")),
  TIMER_CLOCK(EmojiParser.parseToUnicode(":timer_clock:")),
  X(EmojiParser.parseToUnicode(":x:")),
  DONE_CHECK(EmojiParser.parseToUnicode(":heavy_check_mark:")),
  WORLD_MAP(EmojiParser.parseToUnicode(":world_map:")),
  FORK(EmojiParser.parseToUnicode(":fork_and_knife:")),
  MEAT(EmojiParser.parseToUnicode(":meat_on_bone:")),
  RAMEN(EmojiParser.parseToUnicode(":ramen:")),
  NUT(EmojiParser.parseToUnicode(":chestnut:")),
  CALC_DOWN(EmojiParser.parseToUnicode(":arrow_down:")),
  CALC_NORMAL(EmojiParser.parseToUnicode(":arrow_right:")),
  CALC_UP(EmojiParser.parseToUnicode(":arrow_up:")),
  DATA(EmojiParser.parseToUnicode(":page_facing_up:")),
  TROPHY(EmojiParser.parseToUnicode(":trophy:")),
  NONE_ACTIVITY(EmojiParser.parseToUnicode(":frowning_face:")),
  LOW(EmojiParser.parseToUnicode(":snowman_with_snow:")),
  MID_LOW(EmojiParser.parseToUnicode(":shamrock:")),
  MID_UP(EmojiParser.parseToUnicode(":exclamation_heart:")),
  UP_LOW(EmojiParser.parseToUnicode(":pick:")),
  UP_UP(EmojiParser.parseToUnicode(":hammer_and_pick:")),
  HYPER(EmojiParser.parseToUnicode(":biohazard:")),
  RULER(EmojiParser.parseToUnicode(":straight_ruler:")),
  SCALES(EmojiParser.parseToUnicode(":scales:")),
  UNDERAGE(EmojiParser.parseToUnicode(":underage:")),
  MAN_MAGE(EmojiParser.parseToUnicode(":man_mage:")),
  WOMAN_MAGE(EmojiParser.parseToUnicode(":woman_mage:")),
  SPAGHETTI(EmojiParser.parseToUnicode(":spaghetti:"));

  private final String emoji;
}
