package com.ssu.howabouthere.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Chat {
    public enum MessageType {
        ENTER, SEND, RECEIVE
    }

    private @Id @GeneratedValue Long id;
    private MessageType type;
    private String text;
    private String senderId;
    private String receiverId;
    @DateTimeFormat(pattern = "yyyy/MM/dd'T'HH:mm:ss")
    private LocalDateTime dateTime;
}
