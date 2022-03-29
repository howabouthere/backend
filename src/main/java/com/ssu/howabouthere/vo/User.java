package com.ssu.howabouthere.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @NonNull
    private String userId;
    // pbdkf2를 사용하여 암호화
    @NonNull
    private String password;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @NonNull
    private LocalDateTime registeredDate;
    @NonNull
    private String username;
    @NonNull
    private String address;
    @NonNull
    private String email;
}
