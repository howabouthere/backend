package com.ssu.howabouthere.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@Builder
@Getter
@Setter
@RedisHash(value = "refreshToken", timeToLive = 60L)
public class RefreshToken {
    private String key;
    private String value;
}
