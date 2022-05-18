package com.ssu.howabouthere.service.impl;

import com.ssu.howabouthere.dao.BoardDao;
import com.ssu.howabouthere.helper.JwtTokenProvider;
import com.ssu.howabouthere.service.BoardService;
import com.ssu.howabouthere.vo.Board;
import com.ssu.howabouthere.vo.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BoardServiceImplTest {
    @Mock
    private BoardDao boardDao;

    private BoardService boardService;

    private final User user = new User(1L, "1234", LocalDateTime.now(), "park", "admin@hello.com");

    @BeforeEach
    void init() {
        boardService = new BoardServiceImpl(boardDao);
    }

    @Test
    void 게시물_올리기() {
        // given
        Board givenArticle = new Board(1L, "hello", "test", "admin", 126.9038D,
                37.5174D, "서울특별시", "영등포구", "영등포동", "");

        /*
        // when
        boardService.uploadArticle(givenArticle);
        when(boardService.getAllUploadedArticles().get(0)).thenReturn(givenArticle);

        // then
        assertEquals(givenArticle, boardService.getUploadedArticle(1L));
        verify(boardService).uploadArticle(givenArticle);
        verify(boardService.getAllUploadedArticles(), times(1));
        verify(boardService.getUploadedArticle(1L), times(1));
        verifyNoMoreInteractions(boardService);
         */
    }

    @Test
    void 게시물_찾기() {
        // given
        Board givenArticle = new Board(1L, "hello", "test", "admin", 126.9038D,
                37.5174D, "서울특별시", "영등포구", "영등포동", "");

        /*
        // when
        boardService.uploadArticle(givenArticle);
        when(boardService.getUploadedArticle(1L).getTitle()).thenReturn("hello");

        // then

         */
    }


}