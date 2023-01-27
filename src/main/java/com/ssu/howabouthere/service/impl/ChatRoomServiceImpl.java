package com.ssu.howabouthere.service.impl;

import com.ssu.howabouthere.dao.RedisDao;
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
@RequiredArgsConstructor
public class ChatRoomServiceImpl {
    private static final String CHAT_ROOM = "CHAT_ROOM";
    public static final String USER_COUNT = "USER_COUNT";
    public static final String ENTER_INFO = "ENTER_INFO";

    private RedisDao redisDao;

    public List<ChatRoom> findAllRoom() {
        return redisDao.getChatRooms(CHAT_ROOM);
    }

    public ChatRoom findRoomByRoomNo(String roomNo) {
        return redisDao.getChatRoomByRoomNo(CHAT_ROOM, roomNo);
    }

    public ChatRoom createChatRoom(String title) {
        ChatRoom chatRoom = ChatRoom.create(title);
        redisDao.setChatRoomInfo(CHAT_ROOM, chatRoom.getRoomNo(), chatRoom);
        return chatRoom;
    }

    public void setUserEnterInfo(String sessionId, String roomNo) {
        redisDao.setEnterInfo(ENTER_INFO, sessionId, roomNo);
    }

    public String getUserEnterRoomNo(String sessionId) {
        return redisDao.getEnterRoomNo(ENTER_INFO, sessionId);
    }

    public void deleteUserEnterInfo(String sessionId) {
        redisDao.deleteEnterInfo(ENTER_INFO, sessionId);
    }

    public long getUserCount(String roomNo) {
        String roomValue = USER_COUNT + "_" + roomNo;
        return redisDao.getUserCount(roomValue).orElse(0L);
    }

    public long plusUserCount(String roomNo) {
        String roomValue = USER_COUNT + "_" + roomNo;
        return redisDao.plusUserCount(roomValue).orElse(0L);
    }

    public long minusUserCount(String roomNo) {
        String roomValue = USER_COUNT + "_" + roomNo;
        return redisDao.minusUserCount(roomValue).orElse(0L);
    }
}
