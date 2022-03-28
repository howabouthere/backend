package com.ssu.howabouthere.service.impl;

import com.ssu.howabouthere.dao.ChatDao;
import com.ssu.howabouthere.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "chatService")
public class ChatServiceImpl implements ChatService {
    private ChatDao chatDao;

    @Autowired
    public ChatServiceImpl(ChatDao chatDao) {
        this.chatDao = chatDao;
    }


}
