package com.ssu.howabouthere.dao;

import com.ssu.howabouthere.mapper.BoardMapper;
import com.ssu.howabouthere.vo.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Board getUploadedArticle(Long boardId) {
        return boardMapper.selectUploadedArticle(boardId);
    }

    public List<Board> getAllUploadedArticles(String userId) {
        return boardMapper.selectAllUploadedArticlesByUserId(userId);
    }

    public List<Board> getAllUploadedArticles() {
        return boardMapper.selectAllUploadedArticles();
    }

    public void deleteArticle(Long boardId) {
        boardMapper.deleteArticle(boardId);
    }

    public void editArticle(Board board) {
        boardMapper.updateArticle(board);
    }

    public List<Board> getUploadedArticlesByLocation(List<String> locationInfos) {
        Map<String, String> locationMap = new HashMap<>();
        locationMap.put("region_1st_name", locationInfos.get(0));
        locationMap.put("region_2nd_name", locationInfos.get(1));
        locationMap.put("region_3rd_name", locationInfos.get(2));
        locationMap.put("region_4th_name", locationInfos.get(3));

        return boardMapper.selectAllUploadedArticlesByLocation(locationMap);
    }

    public List<Board> getArticlesByUserId(String userId) {
        return boardMapper.selectAllUploadedArticlesByUserId(userId);
    }
}
