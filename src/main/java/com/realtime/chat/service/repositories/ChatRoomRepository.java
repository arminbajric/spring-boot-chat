package com.realtime.chat.service.repositories;

import com.realtime.chat.service.models.ChatRoom;
import com.realtime.chat.service.models.UsersModel;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;

public interface ChatRoomRepository extends MongoRepository<ChatRoom,String> {

         List<ChatRoom> getChatRoomsByUsersModelsContaining(List<UsersModel> room, Pageable pageable);
         boolean existsChatRoomByRoom(String room);
         boolean existsChatRoomByUsersModelsContaining(List<UsersModel> list);
         void deleteChatRoomByRoom(String room);

         ChatRoom getChatRoomByRoom(String room);



}
