package com.ssu.howabouthere.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssu.howabouthere.vo.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisSubscriberService {
    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;

    public void sendMessage(String publishMessage) throws Exception {
        Chat chat = objectMapper.readValue(publishMessage, Chat.class);
        messagingTemplate.convertAndSend("/chat/room/" + chat.getRoomNo(), chat);
    }
}
