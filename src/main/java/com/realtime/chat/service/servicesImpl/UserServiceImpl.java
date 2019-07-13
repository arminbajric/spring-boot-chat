package com.realtime.chat.service.servicesImpl;

import com.realtime.chat.service.models.UsersModel;
import com.realtime.chat.service.repositories.UsersRepository;
import com.realtime.chat.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
@Autowired
private final UsersRepository usersRepository;

    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public boolean checkIfEmailIsUnique(String email) {
        return usersRepository.existsByEmail(email);
    }

    @Override
    public void saveUser(UsersModel usersModel) {
       usersRepository.save(usersModel);
    }

    @Override
    public boolean checkUserLogin(String email, String password) {
        return usersRepository.existsByEmailAndPassword(email, password);
    }
}
