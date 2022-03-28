package com.ssu.howabouthere.mapper;

import com.ssu.howabouthere.vo.Board;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardMapper {
    void insertArticle(Board board);
}
