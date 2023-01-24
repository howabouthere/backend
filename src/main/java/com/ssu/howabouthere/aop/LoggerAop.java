package com.ssu.howabouthere.aop;

import com.ssu.howabouthere.vo.Board;
import com.ssu.howabouthere.vo.ChatMessage;
import com.ssu.howabouthere.vo.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
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

    @Pointcut(value = "execution(* com.ssu.howabouthere.helper.KakaoMapHelper.getDivisionByAxis(..)) && args(longitude, latitude)",
            argNames = "longitude,latitude")
    private void kakaoApiExecution(Double longitude, Double latitude) {}

    @Pointcut(value = "execution(* com.ssu.howabouthere.controller.ChatController.message(..)) && args(chatMessage, token))")
    private void sendMessageExecution(ChatMessage chatMessage, String token) {}

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

    @Before(value = "kakaoApiExecution(longitude, latitude)", argNames = "jp,longitude,latitude")
    public void kakaoApiExecutionAdvisor(JoinPoint jp, Double longitude, Double latitude) {
        logger.info("in method: " + jp.getSignature().getName());
        logger.info("longitude: " + longitude + " and latitude: " + latitude);
    }
/*
    @Around(value = "sendMessageExecution(chatMessage, token)", argNames = "pjp, chatMessage, token")
    public Object sendMessageAroundAdvisor(ProceedingJoinPoint pjp, ChatMessage chatMessage, String token) throws Throwable {
        logger.info(chatMessage.toString());
        logger.info("token : " + token);

        Object retVal = pjp.proceed();

        return retVal;
    }*/
}
