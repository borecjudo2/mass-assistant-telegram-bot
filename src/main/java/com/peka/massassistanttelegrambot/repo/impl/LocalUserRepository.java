package com.peka.massassistanttelegrambot.repo.impl;

import com.peka.massassistanttelegrambot.model.User;
import com.peka.massassistanttelegrambot.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

  private final Map<String, User> db = new HashMap<>();

  @Override
  public User saveUser(User user) {
    db.put(user.getUsername(), user);
    log.info("User saved! " + user);
    return user;
  }

  @Override
  public User getUserByUsername(String username) {
    return db.get(username);
  }
}
