package com.ssu.howabouthere.service.impl;

import com.ssu.howabouthere.dao.BoardDao;
import com.ssu.howabouthere.service.BoardService;
import com.ssu.howabouthere.vo.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "boardService")
public class BoardServiceImpl implements BoardService {
    private BoardDao boardDao;

    @Autowired
    public BoardServiceImpl(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    @Override
    public void uploadArticle(Board board) {
        boardDao.uploadArticle(board);
    }

    @Override
    public Board getUploadedArticle(Long boardId, String userId) {
        return boardDao.getUploadedArticle(boardId, userId);
    }

    @Override
    public List<Board> getAllUploadedArticles(String userId) {
        return boardDao.getAllUploadedArticles(userId);
    }
}
