package com.ssu.howabouthere.service.impl;

import com.ssu.howabouthere.vo.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service("chatRoomService")
public class ChatRoomServiceImpl {
    private static final String CHAT_ROOM = "CHAT_ROOM";
    public static final String USER_COUNT = "USER_COUNT";
    public static final String ENTER_INFO = "ENTER_INFO";

    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, ChatRoom> chatRoomHashOperations;
    private HashOperations<String, String, String> enterInfoHashOperations;
    private ValueOperations<String, Object> userCountValueOperations;

    @Autowired
    public ChatRoomServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        chatRoomHashOperations = this.redisTemplate.opsForHash();
        enterInfoHashOperations = this.redisTemplate.opsForHash();
        userCountValueOperations = this.redisTemplate.opsForValue();
    }

    public List<ChatRoom> findAllRoom() {
        return chatRoomHashOperations.values(CHAT_ROOM);
    }

    public ChatRoom findRoomByRoomNo(String roomNo) {
        return chatRoomHashOperations.get(CHAT_ROOM, roomNo);
    }

    public ChatRoom createChatRoom(String title) {
        ChatRoom chatRoom = ChatRoom.create(title);
        chatRoomHashOperations.put(CHAT_ROOM, chatRoom.getRoomNo(), chatRoom);
        return chatRoom;
    }

    public void setUserEnterInfo(String sessionId, String roomNo) {
        enterInfoHashOperations.put(ENTER_INFO, sessionId, roomNo);
    }

    public String getUserEnterRoomNo(String sessionId) {
        return enterInfoHashOperations.get(ENTER_INFO, sessionId);
    }

    public void deleteUserEnterInfo(String sessionId) {
        enterInfoHashOperations.delete(ENTER_INFO, sessionId);
    }

    public long getUserCount(String roomNo) {
        String roomValue = USER_COUNT + "_" + roomNo;
        return Long.parseLong((String) Optional.ofNullable(
                userCountValueOperations.get(roomValue)).orElse("0"));
    }

    public long plusUserCount(String roomNo) {
        String roomValue = USER_COUNT + "_" + roomNo;
        return Optional.ofNullable(
                userCountValueOperations.increment(roomValue)).orElse(0L);
    }

    public long minusUserCount(String roomNo) {
        String roomValue = USER_COUNT + "_" + roomNo;
        return Optional.ofNullable(
                userCountValueOperations.decrement(roomValue)).orElse(0L);
    }
}
