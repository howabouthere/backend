package com.ssu.howabouthere.mapper;

import com.ssu.howabouthere.vo.ChatMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {
    List<ChatMessage> getChattingLogOfRoom();
}
