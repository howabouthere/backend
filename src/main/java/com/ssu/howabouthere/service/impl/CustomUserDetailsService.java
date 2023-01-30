package com.ssu.howabouthere.service.impl;

import com.ssu.howabouthere.dao.UserDao;
import com.ssu.howabouthere.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service(value = "userDetailsService")
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private UserDao userDao;

    @Autowired
    public CustomUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getMemberInfoByEmail(username);
        if(user != null) {
            return createUserDetails(user);
        }
        else throw new UsernameNotFoundException(username + "의 회원정보가 없습니다.");
    }

    private UserDetails createUserDetails(User user) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRoles().toString());

        return new org.springframework.security.core.userdetails.User(String.valueOf(user.getId()),
                user.getPassword(),
                Collections.singleton(grantedAuthority));
    }
}
