package com.realtime.chat.service.controllers;

import com.realtime.chat.service.models.ChatMessage;
import com.realtime.chat.service.models.ChatMessageModel;
import com.realtime.chat.service.models.OnlineList;
import com.realtime.chat.service.models.OnlineUsers;
import com.realtime.chat.service.repositories.ChatMessageRepository;
import com.realtime.chat.service.repositories.OnlineRepository;
import com.realtime.chat.service.services.OnlinePoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;



@Controller

public class ChatMessageController {

    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private OnlinePoolService onlinePoolService;
    @Autowired
    private OnlineRepository onlineRepository;
    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event) {
        Message msg = event.getMessage();
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(msg);
        String sessionId = accessor.getSessionId();
        System.out.println("Disconnecting "+sessionId);
        onlinePoolService.removeUserFromPool(sessionId);
    }
    @EventListener
    public void onConnectEvent(SessionConnectEvent session) {
        Message msg = session.getMessage();
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(msg);
        String username = accessor.getNativeHeader("username").get(0);
        System.out.println("Incoming "+username);
        onlinePoolService.saveNewuser(username,accessor.getSessionId());

    }
    @RequestMapping("/")
    public String redirect() {
        return "index";
    }





    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    @MessageMapping("/newMessage")
    @SendTo("/topic/newMessage")
    public ChatMessage save(ChatMessageModel chatMessageModel) throws InterruptedException {
        Thread.sleep(1000);
        ChatMessageModel chatMessage = new ChatMessageModel(chatMessageModel.getText(), chatMessageModel.getAuthor(), new Date());
        ChatMessageModel message = chatMessageRepository.save(chatMessage);
        List<ChatMessageModel> chatMessageModelList = chatMessageRepository.findAll(new PageRequest(0, 5, Sort.Direction.DESC, "createDate")).getContent();
        return new ChatMessage(chatMessageModelList.toString());
    }

    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public HttpEntity list() {
        List<ChatMessageModel> chatMessageModelList = chatMessageRepository.findAll(new PageRequest(0, 5, Sort.Direction.DESC, "createDate")).getContent();
        return new ResponseEntity(chatMessageModelList, HttpStatus.OK);
    }

    @RequestMapping(value = "/leave")
    public String leave() {

        return "login";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @MessageMapping("/newUsers")
    @SendTo("/topic/users")
    public HttpEntity getOnline( ChatMessageModel chatMessageModel) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("Activateddd");

        return new ResponseEntity(onlineRepository.findAll(),HttpStatus.OK) ;


    }
}