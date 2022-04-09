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
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "login.do", notes = "로그인하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 404, message = "관리자에게 문의")
    })
    @PostMapping("/login.do")
    public @ResponseBody Map<String, Object> login(@RequestBody @Valid User loginInfo, HttpServletRequest request) {
        String userId = loginInfo.getUserId();
        String password = loginInfo.getPassword();
        Map<String, Object> resultMap = new HashMap<>();

        boolean isMatched = userService.login(userId, password);
        resultMap.put("isLoginOK", isMatched);

        if(isMatched) {
            User loginMember = userService.getMemberInfoById(userId);
            HttpSession session = request.getSession();
            session.setAttribute("loginMember", loginMember);
        }

        return resultMap;
    }

    @ApiOperation(value = "register.do", notes = "회원가입하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 404, message = "관리자에게 문의")
    })
    @PostMapping("/register.do")
    public void register(@RequestBody @Valid User user, HttpServletRequest request) {
        userService.register(user);
    }
}
