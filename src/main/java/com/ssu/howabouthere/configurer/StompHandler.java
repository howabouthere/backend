package com.ssu.howabouthere.configurer;

import com.ssu.howabouthere.helper.JwtTokenProvider;
import com.ssu.howabouthere.service.ChatService;
import com.ssu.howabouthere.service.impl.ChatRoomServiceImpl;
import com.ssu.howabouthere.vo.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
    private JwtTokenProvider jwtTokenProvider;
    private ChatService chatService;
    private ChatRoomServiceImpl chatRoomService;
    private Chat chat;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if(StompCommand.CONNECT == accessor.getCommand()) {
            String jwtToken = accessor.getFirstNativeHeader("token");
            jwtTokenProvider.validateToken(jwtToken);
        } else if(StompCommand.SUBSCRIBE == accessor.getCommand()) {
            String roomNo = chatService.findChatRoomNo(Optional.ofNullable(
                    (String)message.getHeaders().get("destination")).orElse("InvalidRoomNo"));
            String sessionId = (String) message.getHeaders().get("sessionId");
            chatRoomService.setUserEnterInfo(sessionId, roomNo);
            chatRoomService.plusUserCount(roomNo);
            String userId = Optional.ofNullable((Principal)message.getHeaders().get("user"))
                    .map(Principal::getName).orElse("unknown");
            chatService.sendChat(Chat.builder().type(Chat.MessageType.ENTER).roomNo(roomNo).senderId(userId).build());
        } else if(StompCommand.DISCONNECT == accessor.getCommand()) {
            String sessionId = (String) message.getHeaders().get("sessionId");
            String roomNo = chatRoomService.getUserEnterRoomNo(sessionId);
            chatRoomService.minusUserCount(roomNo);
            String userId = Optional.ofNullable((Principal) message.getHeaders().get("user")).map(Principal::getName).orElse("UnknownUser");
            chatService.sendChat(Chat.builder().type(Chat.MessageType.QUIT).roomNo(roomNo).senderId(userId).build());
            chatRoomService.deleteUserEnterInfo(sessionId);
        }
        return ChannelInterceptor.super.preSend(message, channel);
    }
}
