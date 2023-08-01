package com.peka.massassistanttelegrambot.repo.impl;

import com.peka.massassistanttelegrambot.model.User;
import com.peka.massassistanttelegrambot.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DESCRIPTION
 *
 * @author Vladislav Karpeka
 * @version 1.0.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LocalUserRepository implements UserRepository {

  private final Map<String, User> db = new ConcurrentHashMap<>();

  @Override
  public User saveUser(User user) {
    if (user == null) {
      return null;
    }

    db.put(String.valueOf(user.getId()), user);

    log.info(String.format("User saved! ChatID=%s Username=%s", user.getId(), user.getUsername()));
    return user;
  }

  @Override
  public User getUserByUsername(long id) {
    return db.get(String.valueOf(id));
  }
}
