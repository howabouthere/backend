package com.ssu.howabouthere.aop;

import com.ssu.howabouthere.vo.Board;
import com.ssu.howabouthere.vo.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LoggerAop {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut(value = "execution(* com.ssu.howabouthere.controller.UserController.login(..)) && args(user, request)", argNames = "user,request")
    private void loginExecution(User user, HttpServletRequest request) {}

    @Pointcut(value = "execution(* com.ssu.howabouthere.controller.UserController.register(..)) && args(user, request)", argNames = "user,request")
    private void registerExecution(User user, HttpServletRequest request) {}

    @Pointcut(value = "execution(* com.ssu.howabouthere.controller.BoardController.*(..)) && args(board, request)", argNames = "board, request")
    private void boardExecution(Board board, HttpServletRequest request) {}

    @Before(value = "loginExecution(loginUser, request)", argNames = "jp,loginUser,request")
    public void loginAroundAdvisor(JoinPoint jp, User loginUser, HttpServletRequest request) {
        logger.info("login user info : " + loginUser.toString());
    }

    @Before(value = "registerExecution(registerUser, request)", argNames = "jp,registerUser,request")
    public void registerAroundAdvisor(JoinPoint jp, User registerUser, HttpServletRequest request) {
        logger.info("register user info: " + registerUser.toString());
    }

    @Before(value = "boardExecution(board, request)", argNames = "jp,board,request")
    public void uploadArticleAroundAdvisor(JoinPoint jp, Board board, HttpServletRequest request) {
        logger.info("in method: " + jp.getSignature().getName());
        logger.info("board article info: " + board.toString());
    }
}
