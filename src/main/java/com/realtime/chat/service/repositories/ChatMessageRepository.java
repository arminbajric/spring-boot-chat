package com.realtime.chat.service.repositories;

import com.realtime.chat.service.models.ChatMessageModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;


public interface ChatMessageRepository extends MongoRepository<ChatMessageModel, String> {

    List<ChatMessageModel> getChatMessageModelsByRoomOrderByCreateDateDesc(String room);


}