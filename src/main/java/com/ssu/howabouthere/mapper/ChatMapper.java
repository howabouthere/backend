package com.ssu.howabouthere.mapper;

import com.ssu.howabouthere.vo.Chat;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {
    List<Chat> getChattingLogOfRoom();
}
