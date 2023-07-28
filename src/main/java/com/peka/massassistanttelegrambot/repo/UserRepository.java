package com.peka.massassistanttelegrambot.repo;

import com.peka.massassistanttelegrambot.model.User;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
public interface UserRepository {

  User saveUser(User user);

  User getUserByUsername(String username);
}
