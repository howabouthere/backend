package com.ssu.howabouthere.mapper;

import com.ssu.howabouthere.vo.User;
import org.springframework.stereotype.Repository;

@Repository("userMapper")
public interface UserMapper {
    void insertUser(User user);
    User selectMember(String userId);
    int countMemberMatches(String userId, String password);
}
