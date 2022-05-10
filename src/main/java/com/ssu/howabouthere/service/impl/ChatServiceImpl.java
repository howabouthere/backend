package com.ssu.howabouthere.service.impl;

import com.ssu.howabouthere.service.ChatService;
import com.ssu.howabouthere.vo.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service(value = "chatService")
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChannelTopic channelTopic;
    private final RedisTemplate redisTemplate;
    private final ChatRoomServiceImpl chatRoomService;

    public String findChatRoomNo(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1)
            return destination.substring(lastIndex + 1);
        else
            return "";
    }

    public void sendChat(Chat chatMessage) {
        chatMessage.setUserCount(chatRoomService.getUserCount(chatMessage.getRoomNo()));
        if (Chat.MessageType.ENTER.equals(chatMessage.getType())) {
            chatMessage.setText(chatMessage.getSenderId() + "님이 방에 입장했습니다.");
            chatMessage.setSenderId("[알림]");
        } else if (Chat.MessageType.QUIT.equals(chatMessage.getType())) {
            chatMessage.setText(chatMessage.getSenderId() + "님이 방에서 나갔습니다.");
            chatMessage.setSenderId("[알림]");
        }
        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
    }
}
