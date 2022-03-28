package com.ssu.howabouthere.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Board {
    private int boardId;
    private String title;
    // UTF8MB4
    private String text;
    private String userId;
}
