package com.realtime.chat.service.repositories;

import com.realtime.chat.service.models.OnlineUsers;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface OnlineRepository extends MongoRepository<OnlineUsers,String> {
    boolean existsByUsername(String username);
    OnlineUsers getByEmail(String email);
    void deleteOnlineUsersBySessionid(String sessionid);




}
