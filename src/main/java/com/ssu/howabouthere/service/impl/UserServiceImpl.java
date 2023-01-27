package com.ssu.howabouthere.service.impl;

import com.ssu.howabouthere.constants.Constants;
import com.ssu.howabouthere.dao.RedisDao;
import com.ssu.howabouthere.dao.UserDao;
import com.ssu.howabouthere.dto.RefreshToken;
import com.ssu.howabouthere.dto.Token;
import com.ssu.howabouthere.helper.JwtTokenProvider;
import com.ssu.howabouthere.service.UserService;
import com.ssu.howabouthere.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service(value = "userService")
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    private JwtTokenProvider jwtTokenProvider;

    private RedisDao redisDao;

    @Override
    public void register(User user) {
        user.setRegisteredDate(LocalDateTime.now());
        userDao.register(user);
    }

    @Override
    public User getMemberInfoByEmail(String email) {
        return userDao.getMemberInfoByEmail(email);
    }

    @Override
    public Token login(String email, String password) {
        User user = userDao.login(email, password);
        if(user != null) {
            UsernamePasswordAuthenticationToken authenticationToken = user.toAuthentication();

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            Token token = jwtTokenProvider.generateToken(authentication);

            RefreshToken refreshToken = RefreshToken.builder()
                    .key(authentication.getName())
                    .value(token.getRefreshToken())
                    .build();

            redisDao.save(Constants.REFRESH_TOKEN, refreshToken);

            return token;
        }

        return null;
    }

    @Override
    public Token reissueToken(Token token) {
        if(!jwtTokenProvider.validateToken(token.getRefreshToken())) {
            throw new RuntimeException("jwt 리프레시 토큰이 유효하지 않습니다.");
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(token.getAccessToken());

        RefreshToken refreshToken = redisDao.find(Constants.REFRESH_TOKEN, authentication.getName())
                .orElseThrow(() -> new RuntimeException("이미 로그아웃 된 사용자입니다."));

        if(!refreshToken.getValue().equals(token.getRefreshToken())) {
            throw new RuntimeException("토큰의 정보가 올바르지 않습니다.");
        }

        Token newToken = jwtTokenProvider.generateToken(authentication);
        RefreshToken newRefreshToken = RefreshToken.builder()
                        .key(authentication.getName())
                        .value(newToken.getRefreshToken())
                        .build();
        redisDao.save(Constants.REFRESH_TOKEN, newRefreshToken);

        return newToken;
    }
}
