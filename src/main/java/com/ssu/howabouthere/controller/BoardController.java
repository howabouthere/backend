package com.ssu.howabouthere.controller;

import com.ssu.howabouthere.service.BoardService;
import com.ssu.howabouthere.vo.Board;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController(value = "boardController")
@RequestMapping("/board")
public class BoardController {
    private BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @ApiOperation(value = "upload", notes = "업로드 확인")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "관리자에게 문의")
    })
    @PostMapping("/uploadArticle.do")
    public void uploadArticle(@RequestBody Board board, HttpServletRequest request) {
        boardService.uploadArticle(board);
    }

    @ApiOperation(value = "delete", notes = "업로드 확인")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "관리자에게 문의")
    })
    @PostMapping("/deleteArticle.do")
    public void deleteArticle(@RequestBody Board board, HttpServletRequest request) {
        boardService.deleteArticle(board);
    }

    @ApiOperation(value = "edit", notes = "업로드 확인")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "관리자에게 문의")
    })
    @PostMapping("/editArticle.do")
    public void editArticle(@RequestBody Board board, HttpServletRequest request) {
        boardService.editArticle(board);
    }

    @ApiOperation(value = "getAroundLocationArticles", notes = "위치 주변 게시글 찾기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "관리자에게 문의")
    })
    @PostMapping("/getAroundLocationArticle.do")
    public List<Board> getArticlesByLocation(@RequestBody Map<String, Object> axisInfo, HttpServletRequest request) throws Exception {
        return boardService.getAroundLocationArticles(axisInfo);
    }

    @ApiOperation(value = "getArticlesByUserId", notes = "유저 아이디를 사용하여 게시글 찾기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "관리자에게 문의")
    })
    @PostMapping("/getArticlesByUserId.do")
    public List<Board> getArticleByUserId(@RequestBody Board board, HttpServletRequest request) throws Exception {
        return boardService.getArticlesByUserId(board);
    }
}
