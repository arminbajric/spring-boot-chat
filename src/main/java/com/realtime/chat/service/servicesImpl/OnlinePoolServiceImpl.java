package com.realtime.chat.service.servicesImpl;

import com.realtime.chat.service.models.ChatRoom;
import com.realtime.chat.service.models.OnlineUsers;
import com.realtime.chat.service.repositories.ChatRoomRepository;
import com.realtime.chat.service.repositories.OnlineRepository;
import com.realtime.chat.service.services.OnlinePoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OnlinePoolServiceImpl implements OnlinePoolService {
    @Autowired
    private final OnlineRepository onlineRepository;
    @Autowired
    private final ChatRoomRepository chatRoomRepository;

    public OnlinePoolServiceImpl(OnlineRepository onlineRepository, ChatRoomRepository chatRoomRepository) {
        this.onlineRepository = onlineRepository;
        this.chatRoomRepository = chatRoomRepository;
    }

    @Override
    public boolean checkForNickname(String nickname) {
        return false;
    }

    @Override
    public List<OnlineUsers> getOnlineUsers() {
        return onlineRepository.findAll();
    }

    @Override
    public void removeUserFromPool(String nickname) {
           onlineRepository.deleteOnlineUsersBySessionid(nickname);
    }


    @Override
    public void saveNewuser(String username,String email,String session) {
        onlineRepository.save(new OnlineUsers(username,email,session));
    }

    @Override
    public boolean checkIfRoomExists(String room) {
        return chatRoomRepository.existsByRoom(room);
    }

    @Override
    public ChatRoom getRoom(String room) {
        return chatRoomRepository.getByRoom(room);
    }

    @Override
    public void saveRoom(ChatRoom chatRoom) {
        chatRoomRepository.save(chatRoom);
    }
}
