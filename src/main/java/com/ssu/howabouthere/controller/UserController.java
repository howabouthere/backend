package com.ssu.howabouthere.controller;

import com.ssu.howabouthere.service.UserService;
import com.ssu.howabouthere.vo.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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

        boolean isMatched = userService.login(email, password);
        resultMap.put("loginSuccess", isMatched);

        if(isMatched) {
            User loginMember = userService.getMemberInfoByEmail(email);
            HttpSession session = request.getSession();
            session.setAttribute("loginMember", loginMember);
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
}
