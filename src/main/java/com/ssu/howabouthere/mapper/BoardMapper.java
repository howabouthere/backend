package com.ssu.howabouthere.mapper;

import com.ssu.howabouthere.vo.Board;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardMapper {
    void insertArticle(Board board);
    Board selectUploadedArticle(Long boardId, String userId);
    List<Board> selectAllUploadedArticles(String userId);
}
