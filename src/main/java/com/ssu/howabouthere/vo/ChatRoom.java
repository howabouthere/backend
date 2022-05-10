package com.ssu.howabouthere.vo;

import lombok.Data;

import java.util.UUID;

@Data
public class ChatRoom {
    private String roomNo;
    private String title;
    private long userCount;

    public static ChatRoom create(String title) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomNo = UUID.randomUUID().toString();
        chatRoom.title = title;
        return chatRoom;
    }
}
