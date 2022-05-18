package com.ssu.howabouthere.controller;

import com.ssu.howabouthere.helper.JwtTokenProvider;
import com.ssu.howabouthere.service.ChatService;
import com.ssu.howabouthere.service.impl.ChatRoomServiceImpl;
import com.ssu.howabouthere.vo.Chat;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private JwtTokenProvider jwtTokenProvider;
    private ChatRoomServiceImpl chatRoomService;
    private ChatService chatService;

    @Autowired
    public ChatController(JwtTokenProvider jwtTokenProvider, ChatRoomServiceImpl chatRoomService, ChatService chatService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.chatRoomService = chatRoomService;
        this.chatService = chatService;
    }

    @ApiOperation(value = "/chat/message", notes = "메세지 보내기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "관리자에게 문의")
    })
    @MessageMapping("/message")
    public void message(Chat chat, @Header("token") String token) {
        String username = jwtTokenProvider.getUserNameFromJwt(token);
        chat.setSenderId(username);
        chat.setUserCount(chatRoomService.getUserCount(chat.getRoomNo()));
        chatService.sendChat(chat);
    }
}
