package com.ssu.howabouthere.service;

import com.ssu.howabouthere.vo.User;

public interface UserService {
    void register(User user);
    User getMemberInfoById(String userId);
    boolean login(String id, String password);
}
