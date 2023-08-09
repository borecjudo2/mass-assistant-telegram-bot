package com.peka.massassistanttelegrambot.repo;

import com.peka.massassistanttelegrambot.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
public interface MongodbUserRepository extends MongoRepository<User, Long> {

  List<User> findAllByIsResultTaskEnabled(boolean isResultTaskEnabled);
}
