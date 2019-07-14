package com.realtime.chat.service.controllers;

import com.realtime.chat.service.models.*;
import com.realtime.chat.service.repositories.ChatMessageRepository;
import com.realtime.chat.service.repositories.OnlineRepository;
import com.realtime.chat.service.repositories.UsersRepository;
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
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private SimpMessagingTemplate messageSender;
    @EventListener

    public void onDisconnectEvent(SessionDisconnectEvent event) {
        Message msg = event.getMessage();
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(msg);
        String sessionId = accessor.getSessionId();

        onlinePoolService.removeUserFromPool(sessionId);
      messageSender.convertAndSend("/topic/users",onlinePoolService.getOnlineUsers());
    }
    @EventListener
    public void onConnectEvent(SessionConnectEvent session) {
        Message msg = session.getMessage();
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(msg);
        String username = accessor.getNativeHeader("email").get(0);


        onlinePoolService.saveNewuser(usersRepository.getByEmail(username).getUsername(),usersRepository.getByEmail(username).getEmail(),accessor.getSessionId());

    }
    @RequestMapping("/")
    public String redirect() {
        return "index";
    }





    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    @MessageMapping("/newMessage/{room}")
    @SendTo("/topic/newMessage/{room}")
    public  HttpEntity save(ChatMessageModel chatMessageModel,@PathVariable(name = "room")String room) throws InterruptedException {

        System.out.println(room);
        ChatMessageModel chatMessage = new ChatMessageModel(chatMessageModel.getText(), chatMessageModel.getAuthor(), new Date(),chatMessageModel.getRoom(),usersRepository.getByEmail(chatMessageModel.getAuthor()).getUsername());
        ChatMessageModel message = chatMessageRepository.save(chatMessage);

        return  new ResponseEntity(chatMessageRepository.getChatMessageModelsByRoom(chatMessageModel.getRoom()),HttpStatus.OK);
    }

    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public HttpEntity list() {
        List<ChatMessageModel> chatMessageModelList = chatMessageRepository.findAll(new PageRequest(0, 5, Sort.Direction.DESC, "createDate")).getContent();
        return new ResponseEntity(chatMessageModelList, HttpStatus.OK);
    }
    @RequestMapping(value = "/messages/{room}", method = RequestMethod.GET)
    public HttpEntity list(@PathVariable String room) {
        List<ChatMessageModel> chatMessageModelList = chatMessageRepository.getChatMessageModelsByRoom(room);
        return new ResponseEntity(chatMessageModelList, HttpStatus.OK);
    }
    @RequestMapping(value = "/conversation", method = RequestMethod.GET)
    public HttpEntity getConversation(@RequestParam(name = "user")String user,@RequestParam(name = "type")String destination) {

        List<ChatMessageModel> chatMessageModelList = chatMessageRepository.getByTypeAndAuthor(destination,user);
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
        return new ResponseEntity(onlineRepository.findAll(),HttpStatus.OK) ;
    }
    @RequestMapping(value = "/users",method = RequestMethod.GET)
    public HttpEntity getOnlineUsers(){
        return new ResponseEntity((onlinePoolService.getOnlineUsers()),HttpStatus.OK);
    }
    @RequestMapping(value = "/room",method = RequestMethod.GET)
    public HttpEntity getConversationRoom(@RequestParam(name="users")String users){
        String[] user=users.split(" ");

        String newUsers=user[1].concat(" "+user[0]);
        if(onlinePoolService.checkIfRoomExists(users))
        {
            return  new ResponseEntity(onlinePoolService.getRoom(users),HttpStatus.OK);
        }
        else if(onlinePoolService.checkIfRoomExists(newUsers)){
            return  new ResponseEntity(onlinePoolService.getRoom(newUsers),HttpStatus.OK);
        }
        else{
            onlinePoolService.saveRoom(new ChatRoom(users));
            return  new ResponseEntity(new ChatRoom(users),HttpStatus.OK);
        }


    }
}