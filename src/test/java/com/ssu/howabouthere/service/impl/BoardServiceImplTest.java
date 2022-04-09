package com.ssu.howabouthere.service.impl;

import com.ssu.howabouthere.dao.BoardDao;
import com.ssu.howabouthere.mapper.BoardMapper;
import com.ssu.howabouthere.service.BoardService;
import com.ssu.howabouthere.vo.Board;
import com.ssu.howabouthere.vo.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {BoardService.class})
class BoardServiceImplTest {
    @Mock
    private BoardDao boardDao;

    private BoardService boardService;

    private final User user = new User(1L, "admin", "1234", LocalDateTime.now(), "park", "서울특별시", "admin@hello.com");

    @BeforeEach
    void init() {
        boardService = new BoardServiceImpl(boardDao);
    }

    @Test
    void 게시물_올리기() {
        // given
        Board givenArticle = new Board(1L, "hello", "test", "admin", 123, 234);

        // when
        boardService.uploadArticle(givenArticle);
        when(boardDao.getUploadedArticle(1L, "admin")).thenReturn(givenArticle);

        List<Board> articles = new ArrayList<>();
        articles.add(givenArticle);
        when(boardDao.getAllUploadedArticles("admin")).thenReturn(articles);

        // then
        Board article = boardService.getUploadedArticle(1L, "admin");
        assertEquals(givenArticle, article);
        verify(boardDao, times(1)).uploadArticle(article);
        verify(boardDao, times(2)).getUploadedArticle(1L, "admin");
        verify(boardDao, times(1)).getAllUploadedArticles("admin");
        verifyNoMoreInteractions(boardDao);
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