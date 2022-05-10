package com.ssu.howabouthere.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @MessageMapping(".register")
    @SendTo("")
    public void register() {

    }

    @MessageMapping(".send")
    @SendTo("")
    public void send() {

    }

    @MessageMapping(".receive")
    @SendTo()
    public void receive() {

    }
}
