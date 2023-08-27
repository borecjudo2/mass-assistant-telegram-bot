package com.peka.massassistanttelegrambot.repo;

import com.peka.massassistanttelegrambot.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
public interface RxMongodbUserRepository extends ReactiveMongoRepository<User, Long> {
}
