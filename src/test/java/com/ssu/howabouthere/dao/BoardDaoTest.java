package com.ssu.howabouthere.dao;

import com.ssu.howabouthere.vo.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardDaoTest {
    @Autowired
    private BoardDao boardDao;

    @Test
    void 게시물_올리기() {
        Board givenArticle = new Board(1L, "hello", "test", "admin", 126.9038D,
                37.5174D, "서울특별시", "영등포구", "영등포동", "", LocalDateTime.now());

        boardDao.uploadArticle(givenArticle);

        assertEquals(givenArticle, boardDao.getUploadedArticle(1L));
    }

    @Test
    void 여러개의_게시물_올리기() {
        Board a1 = new Board(1L, "hello", "test", "admin", 126.9038D,
                37.5174D, "서울특별시", "영등포구", "영등포동", "",LocalDateTime.now());
        Board a2 = new Board(2L, "hello", "test", "admin", 126.9555D,
                37.4967D, "서울특별시", "동작구", "상도1동", "",LocalDateTime.now());
        Board a3 = new Board(3L, "hello", "test", "admin", 126.9561D,
                37.505D, "서울특별시", "동작구", "흑석동", "",LocalDateTime.now());
        Board a4 = new Board(4L, "hello", "test", "admin", 126.9561D,
                37.4882D, "서울특별시", "동작구", "봉천동", "",LocalDateTime.now());

        boardDao.uploadArticle(a1);
        boardDao.uploadArticle(a2);
        boardDao.uploadArticle(a3);
        boardDao.uploadArticle(a4);
    }
}