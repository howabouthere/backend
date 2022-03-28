package com.ssu.howabouthere.dao;

import com.ssu.howabouthere.mapper.BoardMapper;
import com.ssu.howabouthere.vo.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository(value = "boardDao")
public class BoardDao {
    private BoardMapper boardMapper;

    @Autowired
    public BoardDao(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    public void uploadArticle(Board board) {
        boardMapper.insertArticle(board);
    }
}
