package com.ssu.howabouthere.controller;

import com.ssu.howabouthere.helper.JwtTokenProvider;
import com.ssu.howabouthere.service.ChatService;
import com.ssu.howabouthere.service.impl.ChatRoomServiceImpl;
import com.ssu.howabouthere.vo.ChatMessage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChatController {
    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomServiceImpl chatRoomService;
    private final ChatService chatService;

    @Autowired
    public ChatController(JwtTokenProvider jwtTokenProvider, ChatRoomServiceImpl chatRoomService, ChatService chatService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.chatRoomService = chatRoomService;
        this.chatService = chatService;
    }

    @ApiOperation(value = "/api/chat/message", notes = "메세지 보내기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "관리자에게 문의")
    })
    @MessageMapping("/api/chat/message")
    public void message(ChatMessage chatMessage, @Header("token") String token) {
        String username = jwtTokenProvider.getUserNameFromJwt(token);
        chatMessage.setSenderId(username);
        chatMessage.setUserCount(chatRoomService.getUserCount(chatMessage.getRoomNo()));
        chatService.sendChat(chatMessage);
    }
}
