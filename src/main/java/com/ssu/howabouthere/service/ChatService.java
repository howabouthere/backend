package com.ssu.howabouthere.service;

import com.ssu.howabouthere.vo.ChatMessage;

public interface ChatService {
    void sendChat(ChatMessage chatMessage);
    String findChatRoomNo(String destination);
}
