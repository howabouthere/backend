package com.ssu.howabouthere.controller;

import com.ssu.howabouthere.helper.JwtTokenProvider;
import com.ssu.howabouthere.service.UserService;
import com.ssu.howabouthere.vo.Authority;
import com.ssu.howabouthere.vo.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserControllerTest {
    @Mock
    private UserService userService;

    private UserController userController;

    private HttpServletRequest request;

    private JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

    private User givenUser = new User(1L, "1234", LocalDateTime.now(), "park", "admin@ssu.ac.kr", Authority.ROLE_ADMIN);

    @BeforeEach
    void mock_init() {
        request = Mockito.mock(HttpServletRequest.class);

        userController = new UserController(userService, jwtTokenProvider);
    }
    @Test
    void 로그인하기() {
        // given

        // when
        userController.login(givenUser);
        lenient().when(userService.login("admin", "1234"));

        // then
        verify(userService, times(1)).login("admin", "1234");
        verifyNoMoreInteractions(userService);
    }

    @Test
    void 회원가입() {
        // given

        // when
        userController.register(givenUser, request);
        when(userService.getMemberInfoByEmail(givenUser.getEmail())).thenReturn(givenUser);

        // then
        assertEquals("admin", userService.getMemberInfoByEmail(givenUser.getEmail()).getName());
        verify(userService, times(1)).register(givenUser);
        verify(userService, times(1)).getMemberInfoByEmail(givenUser.getEmail());
        verifyNoMoreInteractions(userService);
    }
}