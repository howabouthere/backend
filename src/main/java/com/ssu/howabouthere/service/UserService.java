package com.ssu.howabouthere.service;

import com.ssu.howabouthere.vo.User;

public interface UserService {
    void register(User user);
    User getMemberInfoByEmail(String email);
    boolean login(String id, String password);
}
