package com.ssu.howabouthere.helper;

import com.ssu.howabouthere.dto.Token;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@PropertySource(value = "classpath:application.properties")
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secretKey;

    private static final String AUTH_KEY = "auth";

    private static final String BEARER = "Bearer";

    private final long tokenValidMillisecond = 1000L * 60 * 60;

    private final long refreshTokenValidMillisecond = 1000L * 60 * 60 * 24 * 7;

    private Key key;

    @PostConstruct
    protected void init() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public Token generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        Date tokenExpireDate = new Date(now + tokenValidMillisecond);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTH_KEY, authorities)
                .setExpiration(tokenExpireDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        Date refreshTokenExpireDate = new Date(now + refreshTokenValidMillisecond);
        String refreshToken = Jwts.builder()
                .setExpiration(refreshTokenExpireDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return Token.builder()
                .grantType(BEARER)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresTime(tokenExpireDate.getTime())
                .build();
    }

    public String getUserNameFromJwt(String jwt) {
        return getClaims(jwt).getId();
    }

    public boolean validateToken(String jwt) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt);
            return true;
        } catch(SecurityException | MalformedJwtException e) {
            log.error("jwt 토큰 인증 오류입니다.");
        } catch(ExpiredJwtException e) {
            log.error("jwt 토큰이 만료되었습니다.");
        } catch(Exception e) {
            log.error("jwt 에러가 발생하였습니다.");
        }
        return false;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTH_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    private Claims getClaims(String jwt) {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();
        } catch(ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
