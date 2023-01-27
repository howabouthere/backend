package com.ssu.howabouthere.controller;

import com.ssu.howabouthere.dto.Token;
import com.ssu.howabouthere.helper.JwtTokenProvider;
import com.ssu.howabouthere.service.UserService;
import com.ssu.howabouthere.vo.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @ApiOperation(value = "login", notes = "로그인하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 404, message = "관리자에게 문의")
    })
    @PostMapping("/login")
    public @ResponseBody Map<String, Object> login(@RequestBody @Valid User loginInfo, HttpServletRequest request) {
        String email = loginInfo.getEmail();
        String password = loginInfo.getPassword();
        Map<String, Object> resultMap = new HashMap<>();

        Token jwtToken = userService.login(email, password);
        if(jwtToken == null) {
            resultMap.put("loginSuccess", false);
        } else {
            resultMap.put("loginSuccess", true);
            resultMap.put("token", jwtToken);
        }


        return resultMap;
    }

    @ApiOperation(value = "register", notes = "회원가입하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 404, message = "관리자에게 문의")
    })
    @PostMapping("/register")
    public @ResponseBody Map<String, Object> register(@RequestBody @Valid User user, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        userService.register(user);
        resultMap.put("success", true);
        return resultMap;
    }

    @GetMapping("/logout")
    public @ResponseBody Map<String, Object> logout() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", true);

        // 토큰관련 수행

        return resultMap;
    }

    @GetMapping("/reissueToken")
    public @ResponseBody Map<String, Object> reissueToken(@RequestBody Token token) {
        Map<String, Object> resultMap = new HashMap<>();

        Token reissuedToken = userService.reissueToken(token);
        if(reissuedToken != null) {
            resultMap.put("isReissued", true);
            resultMap.put("reissuedToken", reissuedToken);
        }

        return resultMap;
    }

    @PostMapping("/authenticate")
    public @ResponseBody Map<String, Object> auth(@RequestBody Map<String, String> paramMap, HttpServletRequest request) {
        String token = paramMap.get("token");
        Map<String, Object> resultMap = new HashMap<>();

        boolean isMatched = jwtTokenProvider.validateToken(token);

        resultMap.put("isTokenValidate", isMatched);
        if(isMatched) {
            String username = jwtTokenProvider.getUserNameFromJwt(token);
            resultMap.put("username", username);
        }

        return resultMap;
    }
}
