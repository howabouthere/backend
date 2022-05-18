package com.ssu.howabouthere.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChatRoomUserInfo {
    private String username;
    private String token;
}
