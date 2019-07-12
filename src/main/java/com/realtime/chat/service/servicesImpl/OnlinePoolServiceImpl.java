package com.realtime.chat.service.servicesImpl;

import com.realtime.chat.service.models.OnlineUsers;
import com.realtime.chat.service.repositories.OnlineRepository;
import com.realtime.chat.service.services.OnlinePoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OnlinePoolServiceImpl implements OnlinePoolService {
    @Autowired
    private final OnlineRepository onlineRepository;

    public OnlinePoolServiceImpl(OnlineRepository onlineRepository) {
        this.onlineRepository = onlineRepository;
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
    public void removeUserFromPool(String session) {
onlineRepository.deleteOnlineUsersBySessionid(session);
    }

    @Override
    public void saveNewuser(String username,String session) {
        onlineRepository.save(new OnlineUsers(username,session));
    }
}
