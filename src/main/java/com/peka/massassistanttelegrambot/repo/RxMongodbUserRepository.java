package com.peka.massassistanttelegrambot.repo;

import com.peka.massassistanttelegrambot.model.User;
import org.springframework.data.repository.reactive.RxJava3CrudRepository;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
public interface RxMongodbUserRepository extends RxJava3CrudRepository<User, Long> {
}
