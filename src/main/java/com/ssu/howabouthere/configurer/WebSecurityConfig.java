package com.ssu.howabouthere.configurer;

import com.ssu.howabouthere.helper.jwt.JwtAccessDeniedHandler;
import com.ssu.howabouthere.helper.jwt.JwtAuthenticationEntryPoint;
import com.ssu.howabouthere.helper.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@PropertySource(value = "classpath:application.properties")
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    @Value("${password.salt}")
    private static String SALT = "haruharu";

    @Value("${password.iterate}")
    private static int ITERATE = 10;

    @Value("${password.hashWidth}")
    private static int HASH_WIDTH = 256;

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                /* csrf를 해제함 */
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                /* X-Frame-Options를 동일 도메인에서 접근을 허용함 */
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                /* 폼 로그인을 하고, 로그인 페이지는 커스텀 페이지로 이동 */
                .and()
                .formLogin()
                .loginPage("/")
                /* 세션을 사용하지 않는다. */
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                /* 모든 요청을 허용한다. */
                .and()
                .authorizeRequests()
                .antMatchers("*").permitAll()
                /* JwtSecurityConfig를 추가한다. */
                .and()
                .apply(new JwtSecurityConfig(jwtTokenProvider))
                .and().build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.httpFirewall(allowUrlEncodedSlashHttpFireWall());
    }

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFireWall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        firewall.setAllowUrlEncodedDoubleSlash(true);

        return firewall;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> encoder = new HashMap<>();

        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder(SALT, ITERATE, HASH_WIDTH);
        pbkdf2PasswordEncoder.setAlgorithm(Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512);
        encoder.put("pbkdf2", pbkdf2PasswordEncoder);

        return new DelegatingPasswordEncoder("pbkdf2", encoder);
    }
}
