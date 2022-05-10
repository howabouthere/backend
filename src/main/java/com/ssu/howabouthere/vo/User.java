package com.ssu.howabouthere.vo;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    private @Id @GeneratedValue Long id;
    // pbdkf2를 사용하여 암호화
    @NonNull
    private String password;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @NonNull
    private LocalDateTime registeredDate;
    @NonNull
    private String name;
    @NonNull
    private String email;
}
