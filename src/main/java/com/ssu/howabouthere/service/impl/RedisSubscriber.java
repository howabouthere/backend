package com.ssu.howabouthere.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssu.howabouthere.vo.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service("redisSubscriber")
@RequiredArgsConstructor
public class RedisSubscriberService {
    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;

    public void sendMessage(String publishMessage) throws Exception {
        ChatMessage chatMessage = objectMapper.readValue(publishMessage, ChatMessage.class);
        messagingTemplate.convertAndSend("/sub/api/chat/room/" + chatMessage.getRoomNo(), chatMessage);
    }
}
