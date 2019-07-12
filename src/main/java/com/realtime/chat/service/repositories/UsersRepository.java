package com.realtime.chat.service.repositories;

import com.realtime.chat.service.models.UsersModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends MongoRepository<UsersModel,Long> {
}
