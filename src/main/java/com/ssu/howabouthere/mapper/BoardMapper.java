package com.ssu.howabouthere.mapper;

import com.ssu.howabouthere.vo.Board;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BoardMapper {
    void insertArticle(Board board);
    Board selectUploadedArticle(Long boardId);
    List<Board> selectAllUploadedArticles();
    List<Board> selectAllUploadedArticlesByUsername(String username);
    void deleteArticle(Long boardId);
    void updateArticle(Board board);
    List<Board> selectAllUploadedArticlesByLocation(Map<String, String> locationMap);
}
