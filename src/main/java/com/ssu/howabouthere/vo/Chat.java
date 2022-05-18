package com.ssu.howabouthere.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Chat implements Serializable {
    public enum MessageType {
        ENTER, SEND, RECEIVE, QUIT
    }

    private @Id @GeneratedValue Long id;
    private MessageType type;
    private String text;
    private String senderId;
    private String receiverId;
    private String roomNo;
    private long userCount;
    @DateTimeFormat(pattern = "yyyy/MM/dd'T'HH:mm:ss")
    private LocalDateTime dateTime;
}
