package com.realtime.chat.service.controllers;

import com.realtime.chat.service.models.ChatMessageModel;
import com.realtime.chat.service.models.ChatRoom;
import com.realtime.chat.service.models.UsersModel;
import com.realtime.chat.service.repositories.ChatMessageRepository;
import com.realtime.chat.service.repositories.OnlineRepository;
import com.realtime.chat.service.repositories.UsersRepository;
import com.realtime.chat.service.services.OnlinePoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.SecureRandom;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Random;


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
      messageSender.convertAndSend("/topic/users",new ResponseEntity((onlinePoolService.getOnlineUsers()),HttpStatus.OK));
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
    public  HttpEntity save(ChatMessageModel chatMessageModel, @PathVariable(name = "room")String room) throws InterruptedException {


        ChatMessageModel chatMessage = new ChatMessageModel(chatMessageModel.getText(), chatMessageModel.getAuthor(), new Date(),chatMessageModel.getRoom(),usersRepository.getByEmail(chatMessageModel.getAuthor()).getUsername());


      try{
          ChatRoom rom=onlinePoolService.getByRoom(chatMessageModel.getRoom());
          if (rom != null) {
              rom.setLastActivity(new Date());
              onlinePoolService.saveRoom(rom);
          }

      }
      catch (NullPointerException e){
          e.printStackTrace();
      }


         ChatMessageModel message = chatMessageRepository.save(chatMessage);

        return  new ResponseEntity(chatMessageRepository.getChatMessageModelsByRoomOrderByCreateDateDesc(chatMessageModel.getRoom()),HttpStatus.OK);
    }

    @RequestMapping(value = "/messages/Public", method = RequestMethod.GET)
    public HttpEntity list() {
        List<ChatMessageModel> chatMessageModelList = chatMessageRepository.getChatMessageModelsByRoomOrderByCreateDateDesc("Public");
        return new ResponseEntity(chatMessageModelList, HttpStatus.OK);
    }
    @RequestMapping(value = "/room-messages", method = RequestMethod.GET)
    public HttpEntity list(@RequestParam(name = "users") String room) {

        List<ChatMessageModel> chatMessageModelList = chatMessageRepository.getChatMessageModelsByRoomOrderByCreateDateDesc(room);
        return new ResponseEntity(chatMessageModelList, HttpStatus.OK);
    }
    @RequestMapping(value = "/conversation", method = RequestMethod.GET)
    public HttpEntity getConversation(@RequestParam(name = "user")String user) {
         List<UsersModel> userModel=new ArrayList<>();
         userModel.add(usersRepository.getByEmail(user));
         List<ChatMessageModel> chatList=new ArrayList<ChatMessageModel>();
        Pageable filter =
                PageRequest.of(0, 5, Sort.by("lastActivity").descending());
         List<ChatRoom> rooms=onlinePoolService.getByUserModel(userModel, filter);
        List<ChatRoom> temp=new ArrayList<>();
         if(rooms.size()>10){
              temp=rooms.subList(0,10);
         }
         else{
             temp=rooms;
         }

         for(int i=0;i<rooms.size();i++){
            filter =
                     PageRequest.of(0, 1);
             chatList.add(chatMessageRepository.getChatMessageModelsByRoomOrderByCreateDateDesc(temp.get(i).getName()).get(0));
         }

        return new ResponseEntity(chatList, HttpStatus.OK);
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
    @RequestMapping(value = "/room",method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity getConversationRoom(@RequestBody List<UsersModel> usersModels){
       List<UsersModel> list=new ArrayList<>();
       list.add(usersRepository.getByEmail(usersModels.get(0).getEmail()));
        list.add(usersRepository.getByEmail(usersModels.get(1).getEmail()));

        Pageable filter =
                PageRequest.of(0, 1);
    if(onlinePoolService.checkIfRoomExists(list)) {
        return new ResponseEntity(onlinePoolService.getRoom(list,filter), HttpStatus.OK);
    }
    else{

        String generatedRoom = randomString(20);
        while(onlinePoolService.checkByRoomName(generatedRoom)==true) {
            generatedRoom = randomString(20);
        }

        onlinePoolService.saveRoom(new ChatRoom(generatedRoom,new Date(),list));
        return new ResponseEntity(onlinePoolService.getRoom(list,filter), HttpStatus.OK);
    }
    }
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }
}