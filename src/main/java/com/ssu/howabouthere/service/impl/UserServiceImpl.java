package com.ssu.howabouthere.service.impl;

import com.ssu.howabouthere.dao.UserDao;
import com.ssu.howabouthere.service.UserService;
import com.ssu.howabouthere.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void register(User user) {
        user.setRegisteredDate(LocalDateTime.now());
        userDao.register(user);
    }

    @Override
    public User getMemberInfoByEmail(String email) {
        User user = userDao.getMemberInfoByEmail(email);

        return user;
    }

    @Override
    public boolean login(String userId, String password) {
        return userDao.login(userId, password);
    }
}
