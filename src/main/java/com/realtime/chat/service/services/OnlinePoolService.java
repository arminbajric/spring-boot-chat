package com.realtime.chat.service.services;

import com.realtime.chat.service.models.OnlineUsers;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OnlinePoolService {
    boolean checkForNickname(String nickname);
    List<OnlineUsers> getOnlineUsers();
    void removeUserFromPool(String nickname);
    void saveNewuser(String username,String session);

}
