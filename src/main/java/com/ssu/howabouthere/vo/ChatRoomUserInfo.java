package com.ssu.howabouthere.vo;

import com.ssu.howabouthere.dto.Token;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChatRoomUserInfo {
    private String username;
    private Token accessToken;
}
