package com.ssu.howabouthere.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private String grantType;
    private String accessToken;
    private long accessTokenExpiresTime;
    private String refreshToken;
    private String key;
}
