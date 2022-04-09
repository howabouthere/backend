package com.ssu.howabouthere.dao;

import com.ssu.howabouthere.mapper.UserMapper;
import com.ssu.howabouthere.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDao {
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserDao(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userMapper.insertUser(user);
    }

    public User getMemberInfoById(String userId) {
        return userMapper.selectMember(userId);
    }

    public boolean login(String userId, String password) {
        User loginUser = userMapper.selectMember(userId);

        return passwordEncoder.matches(password, loginUser.getPassword());
    }


}
