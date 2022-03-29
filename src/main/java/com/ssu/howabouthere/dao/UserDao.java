package com.ssu.howabouthere.dao;

import com.ssu.howabouthere.mapper.UserMapper;
import com.ssu.howabouthere.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;

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

    public boolean login(String id, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        int countMemberMatches = userMapper.countMemberMatches(id, encodedPassword);

        if(countMemberMatches == 1) {
            return true;
        } else {
            return false;
        }
    }


}
