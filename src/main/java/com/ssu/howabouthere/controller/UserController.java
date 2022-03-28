package com.ssu.howabouthere.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/login.do")
    public void login() {

    }

    @RequestMapping("/register.do")
    public void register() {

    }
}
