package com.ssu.howabouthere.service.impl;

import com.ssu.howabouthere.dao.BoardDao;
import com.ssu.howabouthere.helper.KakaoMapHelper;
import com.ssu.howabouthere.service.BoardService;
import com.ssu.howabouthere.vo.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service(value = "boardService")
public class BoardServiceImpl implements BoardService {
    private BoardDao boardDao;
    private KakaoMapHelper kakaoMapHelper = new KakaoMapHelper();

    @Autowired
    public BoardServiceImpl(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    @Override
    public void uploadArticle(Board board) {
        boardDao.uploadArticle(board);
    }

    @Override
    public Board getUploadedArticle(Long boardId) {
        return boardDao.getUploadedArticle(boardId);
    }

    @Override
    public List<Board> getAllUploadedArticles(String userId) {
        return boardDao.getAllUploadedArticles(userId);
    }

    @Override
    public List<Board> getAllUploadedArticles() {
        return boardDao.getAllUploadedArticles();
    }

    @Override
    public void deleteArticle(Board board) {
        Long boardId = board.getId();

        boardDao.deleteArticle(boardId);
    }

    @Override
    public void editArticle(Board board) {
        boardDao.editArticle(board);
    }

    @Override
    public List<Board> getAroundLocationArticles(Map<String, Object> axisInfo) throws Exception {
        Double longitude = (Double) axisInfo.get("longitude"), latitude = (Double) axisInfo.get("latitude");

        List<String> locationInfos = kakaoMapHelper.getDivisionByAxis(longitude, latitude);

        List<Board> boardsByLocation = boardDao.getUploadedArticlesByLocation(locationInfos);

        return boardsByLocation;
    }

    @Override
    public List<Board> getArticlesByUserId(Board board) {
        return boardDao.getArticlesByUserId(board.getUserName());
    }
}
