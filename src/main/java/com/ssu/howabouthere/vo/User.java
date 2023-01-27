package com.ssu.howabouthere.vo;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User implements Serializable {
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
    @Enumerated(EnumType.STRING)
    @NonNull
    private Authority roles;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
