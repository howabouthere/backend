package com.ssu.howabouthere.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Board {
    private @Id @GeneratedValue Long id;
    private String title;
    // UTF8MB4
    private String text;
    private String userId;
    private int longitude;
    private int latitude;
}
