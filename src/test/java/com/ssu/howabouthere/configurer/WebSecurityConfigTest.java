package com.ssu.howabouthere.configurer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WebSecurityConfigTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void passwordEncoder_테스트() {
        // given
        String password = "1234";

        // when
        String encoded = passwordEncoder.encode(password);

        // then
        System.out.println(encoded);
    }
}