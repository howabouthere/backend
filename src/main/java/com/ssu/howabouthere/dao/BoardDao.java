package com.ssu.howabouthere.dao;

import com.ssu.howabouthere.mapper.BoardMapper;
import com.ssu.howabouthere.vo.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public Board getUploadedArticle(Long boardId, String userId) {
        return boardMapper.selectUploadedArticle(boardId, userId);
    }

    public List<Board> getAllUploadedArticles(String userId) {
        return boardMapper.selectAllUploadedArticles(userId);
    }
}
