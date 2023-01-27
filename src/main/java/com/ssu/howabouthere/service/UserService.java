package com.ssu.howabouthere.service;

import com.ssu.howabouthere.dto.Token;
import com.ssu.howabouthere.vo.User;

public interface UserService {
    void register(User user);
    User getMemberInfoByEmail(String email);
    Token login(String email, String password);
    Token reissueToken(Token token);
}
