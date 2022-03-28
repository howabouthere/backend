package com.ssu.howabouthere.service.impl;

import com.ssu.howabouthere.dao.BoardDao;
import com.ssu.howabouthere.mapper.BoardMapper;
import com.ssu.howabouthere.service.BoardService;
import com.ssu.howabouthere.vo.Board;
import com.ssu.howabouthere.vo.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = {BoardService.class})
class BoardServiceImplTest {
    @Mock
    private BoardMapper boardMapper;

    @InjectMocks
    private BoardDao boardDao = new BoardDao(boardMapper);

    @InjectMocks
    private BoardService boardService = new BoardServiceImpl(boardDao);

    private final User user = new User("admin", "1234", LocalDateTime.now(), "park", "서울특별시", "admin@hello.com");

    @Test
    void mock_테스트() {
        assertNotNull(boardService);
    }

    @Test
    void 게시물_올리기() {
        // given
        Board board = new Board(1, "hello", "test", "admin");

        // when
        boardService.uploadArticle(board);

        // then
        List<Board> 게시물 = boardService.getUploadedArticle(1, "admin");
        assertEquals(게시물.get(0), board);
    }
/*
    @Test
    void 게시물_삭제() {
        // given
        Board board = new Board(1, "hello", "test", "admin");

        // when
        boardService.uploadArticle(board);
        boardService.deleteArticle(1);

        // then
        List<Board> 게시물 = boardService.findArticle(1, "admin");
        assertNull(게시물);
    }

    @Test
    void 게시물_검색() {
        // given
        Board board = new Board(1, "hello", "test", "admin");

        // when
        boardService.uploadArticle(board);
        List<Board> 찾은_게시물 = boardService.findArticle("hello", "admin");

        // then
        assertNotNull(찾은_게시물);
    }*/
}