package com.realtime.chat.service.controllers;

import com.realtime.chat.service.models.ChatMessage;
import com.realtime.chat.service.models.ChatMessageModel;
import com.realtime.chat.service.repositories.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.Date;
import java.util.List;



@Controller

public class ChatMessageController {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

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
}