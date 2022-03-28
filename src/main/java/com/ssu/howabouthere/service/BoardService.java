package com.ssu.howabouthere.service;

import com.ssu.howabouthere.vo.Board;

import java.util.List;

public interface BoardService {
    void uploadArticle(Board board);
    List<Board> getUploadedArticle(int boardId, String userId);
}
