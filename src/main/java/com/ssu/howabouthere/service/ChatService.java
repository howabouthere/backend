package com.ssu.howabouthere.service;

import com.ssu.howabouthere.vo.Chat;

public interface ChatService {
    void sendChat(Chat chatMessage);
    String findChatRoomNo(String destination);
}
