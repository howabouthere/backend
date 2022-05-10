package com.ssu.howabouthere.controller;

import com.ssu.howabouthere.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatRoomController {
    private final ChatService chatService;

    public ChatRoomController(@Autowired ChatService chatService) {
        this.chatService = chatService;
    }
}
