package com.ssu.howabouthere.configurer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@PropertySource(value = "classpath:application.properties")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${password.salt}")
    private static String SALT = "haruharu";

    @Value("${password.iterate}")
    private static int ITERATE = 10;

    @Value("${password.hashWidth}")
    private static int HASH_WIDTH = 256;

    @Bean
    public PasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> encoder = new HashMap<>();

        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder(SALT, ITERATE, HASH_WIDTH);
        pbkdf2PasswordEncoder.setAlgorithm(Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512);
        encoder.put("pbkdf2", pbkdf2PasswordEncoder);

        return new DelegatingPasswordEncoder("pbkdf2", encoder);
    }
}
