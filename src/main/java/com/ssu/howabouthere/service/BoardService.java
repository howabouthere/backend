package com.ssu.howabouthere.service;

import com.ssu.howabouthere.vo.Board;

import java.util.List;

public interface BoardService {
    void uploadArticle(Board board);
    Board getUploadedArticle(Long boardId, String userId);
    List<Board> getAllUploadedArticles(String userId);
}
