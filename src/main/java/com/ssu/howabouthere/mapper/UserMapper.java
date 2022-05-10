package com.ssu.howabouthere.mapper;

import com.ssu.howabouthere.vo.User;
import org.springframework.stereotype.Repository;

@Repository("userMapper")
public interface UserMapper {
    void insertUser(User user);
    User selectMemberByEmail(String email);
    int countMemberMatches(String email, String password);
}
