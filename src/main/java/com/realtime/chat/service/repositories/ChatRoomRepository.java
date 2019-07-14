package com.realtime.chat.service.repositories;

import com.realtime.chat.service.models.ChatRoom;
import com.realtime.chat.service.models.UsersModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRoomRepository extends MongoRepository<ChatRoom,String> {
         ChatRoom getByRoom(String room);
         boolean existsByRoom(String room);
}
