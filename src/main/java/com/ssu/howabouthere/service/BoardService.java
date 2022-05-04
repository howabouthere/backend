package com.ssu.howabouthere.service;

import com.ssu.howabouthere.vo.Board;

import java.util.List;
import java.util.Map;

public interface BoardService {
    void uploadArticle(Board board);
    Board getUploadedArticle(Long boardId);
    List<Board> getAllUploadedArticles();
    List<Board> getAllUploadedArticles(String userId);
    void deleteArticle(Board board);
    void editArticle(Board board);
    List<Board> getAroundLocationArticles(Map<String, Object> axisInfo) throws Exception;
    List<Board> getArticlesByUserId(Board board);
}
