package com.ssu.howabouthere.controller;

import com.ssu.howabouthere.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "boardController")
@RequestMapping("/board")
public class BoardController {
    private BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @RequestMapping("/uploadArticle.do")
    public void uploadArticle() {

    }

    @RequestMapping("/deleteArticle.do")
    public void deleteArticle() {

    }

    @RequestMapping("/editArticle.do")
    public void editArticle() {

    }
}
