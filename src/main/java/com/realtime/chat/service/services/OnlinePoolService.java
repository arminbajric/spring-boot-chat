package com.realtime.chat.service.services;

import com.realtime.chat.service.models.ChatRoom;
import com.realtime.chat.service.models.OnlineUsers;
import com.realtime.chat.service.models.UsersModel;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OnlinePoolService {
    boolean checkForNickname(String nickname);
    List<OnlineUsers> getOnlineUsers();
    void removeUserFromPool(String nickname);
    void saveNewuser(String username,String email,String session);
    boolean checkIfRoomExists(List<UsersModel> room);
    List<ChatRoom> getRoom(List<UsersModel> list,Pageable pageable);
    void saveRoom(ChatRoom chatRoom);
    boolean checkByRoomName(String room);
    List<ChatRoom> getByUserModel(List<UsersModel> usersModel, Pageable pageable);
    ChatRoom getByRoom(String room);

}
