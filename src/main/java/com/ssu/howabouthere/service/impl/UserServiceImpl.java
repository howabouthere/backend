package com.ssu.howabouthere.service.impl;

import com.ssu.howabouthere.dao.UserDao;
import com.ssu.howabouthere.service.UserService;
import com.ssu.howabouthere.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void register(User user) {
        userDao.register(user);
    }

    @Override
    public User getMemberInfoById(String userId) {
        User user = userDao.getMemberInfoById(userId);

        return user;
    }

    @Override
    public boolean login(String userId, String password) {
        return userDao.login(userId, password);
    }
}
