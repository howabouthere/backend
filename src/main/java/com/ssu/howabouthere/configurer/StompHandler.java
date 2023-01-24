package com.ssu.howabouthere.configurer;

import com.ssu.howabouthere.helper.JwtTokenProvider;
import com.ssu.howabouthere.service.ChatService;
import com.ssu.howabouthere.service.impl.ChatRoomServiceImpl;
import com.ssu.howabouthere.vo.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;

@Component
public class StompHandler implements ChannelInterceptor {
    private final JwtTokenProvider jwtTokenProvider;
    private final ChatService chatService;
    private final ChatRoomServiceImpl chatRoomService;

    private final Logger logger = LoggerFactory.getLogger(StompHandler.class);

    @Autowired
    public StompHandler(JwtTokenProvider jwtTokenProvider, ChatService chatService, ChatRoomServiceImpl chatRoomService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.chatRoomService = chatRoomService;
        this.chatService = chatService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if(StompCommand.CONNECT == accessor.getCommand()) {
            String jwtToken = accessor.getFirstNativeHeader("token");
            logger.info("connect {}", jwtToken);
            jwtTokenProvider.validateToken(jwtToken);
        } else if(StompCommand.SUBSCRIBE == accessor.getCommand()) {
            String roomNo = chatService.findChatRoomNo(Optional.ofNullable(
                    (String)message.getHeaders().get("simpDestination")).orElse("InvalidRoomNo"));
            String sessionId = (String) message.getHeaders().get("simpSessionId");

            chatRoomService.setUserEnterInfo(sessionId, roomNo);
            chatRoomService.plusUserCount(roomNo);

            chatService.sendChat(ChatMessage.builder().type(ChatMessage.MessageType.ENTER).roomNo(roomNo).build());
        } else if(StompCommand.DISCONNECT == accessor.getCommand()) {
            String sessionId = (String) message.getHeaders().get("simpSessionId");
            String roomNo = chatRoomService.getUserEnterRoomNo(sessionId);

            chatRoomService.minusUserCount(roomNo);

            chatService.sendChat(ChatMessage.builder().type(ChatMessage.MessageType.QUIT).roomNo(roomNo).build());
            chatRoomService.deleteUserEnterInfo(sessionId);
        }
        return message;
    }
}
