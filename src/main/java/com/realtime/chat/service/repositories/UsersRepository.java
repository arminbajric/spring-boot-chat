package com.realtime.chat.service.repositories;

import com.realtime.chat.service.models.UsersModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public interface UsersRepository extends MongoRepository<UsersModel,Long> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndPassword(String email,String password);
}
