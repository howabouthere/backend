package com.ssu.howabouthere.controller;

import com.ssu.howabouthere.helper.JwtTokenProvider;
import com.ssu.howabouthere.service.impl.ChatRoomServiceImpl;
import com.ssu.howabouthere.vo.ChatMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class ChatMessageControllerTest {

    @Autowired
    private ChatController controller;

    @Autowired
    private ChatRoomServiceImpl chatRoomService;

    private JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

    @Test
    void 테스트() {
        //given
        ChatMessage givenChatMessage = ChatMessage.builder()
                .id(10L)
                .roomNo("d9caae20-5172-44ca-8662-a7064738e300")
                .senderId("admin")
                .text("here")
                .type(ChatMessage.MessageType.TALK)
                .userCount(1L)
                .build();

    }
}