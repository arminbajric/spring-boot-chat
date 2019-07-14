package com.realtime.chat.service.controllers;

import com.realtime.chat.service.common.UserLoginData;
import com.realtime.chat.service.common.unique;
import com.realtime.chat.service.common.userLogin;
import com.realtime.chat.service.models.UsersModel;
import com.realtime.chat.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/user-management")
public class UsersController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/new",method = RequestMethod.POST)
    public HttpEntity saveUser(@RequestBody UsersModel usersModel)
    {

            if(!userService.checkIfEmailIsUnique(usersModel.getEmail())) {
                userService.saveUser(usersModel);
                return new ResponseEntity(HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity(HttpStatus.CONFLICT);
            }
    }
    @RequestMapping(value = "/check-unique",method = RequestMethod.GET)
    public HttpEntity checkIfUserIsUnique(@RequestParam(name = "email")String email)
    {
          return new ResponseEntity(new unique(userService.checkIfEmailIsUnique(email)),HttpStatus.OK);
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public HttpEntity checkUserLogin(@RequestBody UserLoginData usersModel)
    {
        boolean emailExists=userService.checkIfEmailIsUnique(usersModel.getEmail());
      
        if(emailExists) {
            return new ResponseEntity(new userLogin(emailExists,userService.checkUserLogin(usersModel.getEmail(),usersModel.getPassword())), HttpStatus.OK);
        }
        else{
            return new ResponseEntity(new userLogin(false,false), HttpStatus.OK);
        }
        }
}
