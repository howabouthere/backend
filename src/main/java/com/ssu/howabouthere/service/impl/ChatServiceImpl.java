package com.ssu.howabouthere.service.impl;

import com.ssu.howabouthere.constants.SessionConstants;
import com.ssu.howabouthere.service.ChatService;
import com.ssu.howabouthere.vo.ChatMessage;
import com.ssu.howabouthere.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service(value = "chatService")
public class ChatServiceImpl implements ChatService {
    private final ChannelTopic channelTopic;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChatRoomServiceImpl chatRoomService;

    @Autowired
    public ChatServiceImpl(ChannelTopic channelTopic, RedisTemplate<String, Object> redisTemplate, ChatRoomServiceImpl chatRoomService) {
        this.channelTopic = channelTopic;
        this.redisTemplate = redisTemplate;
        this.chatRoomService = chatRoomService;
    }

    public String findChatRoomNo(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1)
            return destination.substring(lastIndex + 1);
        else
            return "";
    }

    public void sendChat(ChatMessage chatMessage) {
        chatMessage.setUserCount(chatRoomService.getUserCount(chatMessage.getRoomNo()));

        if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
            chatMessage.setText(chatMessage.getSenderId() + "님이 방에 입장했습니다.");
            chatMessage.setSenderId("[알림]");
        } else if (ChatMessage.MessageType.QUIT.equals(chatMessage.getType())) {
            chatMessage.setText(chatMessage.getSenderId() + "님이 방에서 나갔습니다.");
            chatMessage.setSenderId("[알림]");
        }

        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
    }
}
