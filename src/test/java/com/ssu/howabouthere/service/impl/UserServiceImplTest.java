package com.ssu.howabouthere.service.impl;

import com.ssu.howabouthere.dao.UserDao;
import com.ssu.howabouthere.service.UserService;
import com.ssu.howabouthere.vo.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserServiceImplTest {
    @Mock
    private UserDao userDao;

    private UserService userService;

    private User givenUser = new User(1L, "admin", "1234", LocalDateTime.now(), "park", "서울특별시", "admin@ssu.ac.kr");

    @BeforeEach
    void mock_주입() {
        this.userService = new UserServiceImpl(userDao);
    }

    @Test
    void 회원가입() {
        // given

        // when
        userService.register(givenUser);
        when(userDao.getMemberInfoById("admin")).thenReturn(givenUser);

        // then
        assertEquals(userService.getMemberInfoById("admin"), givenUser);
        verify(userDao, times(1)).getMemberInfoById("admin");
        verify(userDao, times(1)).register(givenUser);
        verifyNoMoreInteractions(userDao);
    }

    @Test
    void 로그인() {
        // given
        String id = "admin";
        String password = "1234";

        // when
        userService.login(id, password);
        when(userDao.login(id, password)).thenReturn(true);

        // then
        assertTrue(userService.login(id, password));
        verify(userDao, times(2)).login(id, password);
        verifyNoMoreInteractions(userDao);
    }
}