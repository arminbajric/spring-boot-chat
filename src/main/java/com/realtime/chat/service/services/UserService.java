package com.realtime.chat.service.services;

import com.realtime.chat.service.models.UsersModel;
import org.springframework.stereotype.Service;


public interface UserService {
    boolean checkIfEmailIsUnique(String email);
    void saveUser(UsersModel usersModel);
    boolean checkUserLogin(String email,String password);
}
