package com.ssu.howabouthere.service.impl;

import com.ssu.howabouthere.dao.UserDao;
import com.ssu.howabouthere.helper.JwtTokenProvider;
import com.ssu.howabouthere.service.UserService;
import com.ssu.howabouthere.vo.Authority;
import com.ssu.howabouthere.vo.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserServiceImplTest {
    @Mock
    private UserDao userDao;

    @Mock
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    private User givenUser = new User(1L, "1234", LocalDateTime.now(), "park", "admin@ssu.ac.kr", Authority.ROLE_ADMIN);

    @Test
    void 회원가입() {
        // given

        // when
        userService.register(givenUser);
        when(userDao.getMemberInfoByEmail("admin@ssu.ac.kr")).thenReturn(givenUser);

        // then
        assertEquals(userService.getMemberInfoByEmail("admin@ssu.ac.kr"), givenUser);
        verify(userDao, times(1)).getMemberInfoByEmail("admin@ssu.ac.kr");
        verify(userDao, times(1)).register(givenUser);
        verifyNoMoreInteractions(userDao);
    }

    @Test
    void 로그인() {
        // given
        String email = "admin@ssu.ac.kr";
        String password = "1234";

        // when
        userService.login(email, password);
        when(userDao.login(email, password));

        // then
        userService.login(email, password);
        verify(userDao, times(2)).login(email, password);
        verifyNoMoreInteractions(userDao);
    }
}