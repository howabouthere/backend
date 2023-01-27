package com.ssu.howabouthere.dao;

import com.ssu.howabouthere.dto.RefreshToken;
import com.ssu.howabouthere.vo.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository(value = "redisDao")
public class RedisDao {
    private RedisTemplate<String, String> redisTemplate;

    public void setChatRoomInfo(String key, String sessionId, ChatRoom chatRoom) {
        HashOperations<String, String, ChatRoom> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(key, sessionId, chatRoom);
    }

    public List<ChatRoom> getChatRooms(String key) {
        return redisTemplate.opsForHash().values(key).stream()
                .map(obj -> (ChatRoom)obj)
                .collect(Collectors.toList());
    }

    public ChatRoom getChatRoomByRoomNo(String key, String roomNo) {
        return (ChatRoom) redisTemplate.opsForHash().get(key, roomNo);
    }

    public void setEnterInfo(String key, String sessionId, String roomNo) {
        redisTemplate.opsForHash().put(key, sessionId, roomNo);
    }

    public String getEnterRoomNo(String key, String sessionId) {
        return (String) redisTemplate.opsForHash().get(key, sessionId);
    }

    public void deleteEnterInfo(String key, String sessionId) {
        redisTemplate.opsForHash().delete(key, sessionId);
    }

    public OptionalLong getUserCount(String key) {
        String userCount = redisTemplate.opsForValue().get(key);
        if(userCount != null)
            return OptionalLong.of(Long.parseLong(userCount));
        return OptionalLong.empty();
    }

    public OptionalLong plusUserCount(String key) {
        String userCount = redisTemplate.opsForValue().get(key);
        if(userCount == null)
            userCount = "0";

        long userCountLong = Long.parseLong(userCount);
        userCountLong += 1;

        redisTemplate.opsForValue().set(key, userCountLong + "");

        return OptionalLong.of(userCountLong);
    }

    public OptionalLong minusUserCount(String key) {
        String userCount = redisTemplate.opsForValue().get(key);
        if(userCount == null)
            userCount = "0";

        long userCountLong = Long.parseLong(userCount);
        userCountLong -= 1;

        redisTemplate.opsForValue().set(key, userCountLong + "");

        return OptionalLong.of(userCountLong);
    }

    public void save(String key, RefreshToken refreshToken) {
        redisTemplate.opsForHash().put(key, refreshToken.getKey(), refreshToken.getValue());
    }

    public Optional<RefreshToken> find(String key, String principalName) {
        return Optional.ofNullable((RefreshToken) redisTemplate.opsForHash().get(key, principalName));
    }
}
