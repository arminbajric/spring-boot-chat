package com.realtime.chat.service.servicesImpl;

import com.realtime.chat.service.models.ChatRoom;
import com.realtime.chat.service.models.OnlineUsers;
import com.realtime.chat.service.models.UsersModel;
import com.realtime.chat.service.repositories.ChatRoomRepository;
import com.realtime.chat.service.repositories.OnlineRepository;
import com.realtime.chat.service.services.OnlinePoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
    public boolean checkIfRoomExists(List<UsersModel> room) {
        return chatRoomRepository.existsChatRoomByUsersModelsContaining(room);
    }

    @Override
    public List<ChatRoom> getRoom(List<UsersModel> room,Pageable pageable) {
        return chatRoomRepository.getChatRoomsByUsersModelsContaining(room,pageable);
    }

    @Override
    public void saveRoom(ChatRoom chatRoom) {
        chatRoomRepository.save(chatRoom);
    }

    @Override
    public boolean checkByRoomName(String room) {
        return chatRoomRepository.existsChatRoomByRoom(room);
    }

    @Override
    public List<ChatRoom> getByUserModel(List<UsersModel> usersModel, Pageable pageable) {
        return chatRoomRepository.getChatRoomsByUsersModelsContaining(usersModel,pageable);
    }

    @Override
    public ChatRoom getByRoom(String room) {
        return chatRoomRepository.getChatRoomByRoom(room);
    }




}
