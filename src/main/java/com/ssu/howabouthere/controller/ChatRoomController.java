package com.ssu.howabouthere.controller;

import com.ssu.howabouthere.constants.Constants;
import com.ssu.howabouthere.helper.JwtTokenProvider;
import com.ssu.howabouthere.service.impl.ChatRoomServiceImpl;
import com.ssu.howabouthere.vo.ChatRoom;
import com.ssu.howabouthere.vo.ChatRoomUserInfo;
import com.ssu.howabouthere.vo.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatRoomController {
    private ChatRoomServiceImpl chatRoomService;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public ChatRoomController(ChatRoomServiceImpl chatRoomService, JwtTokenProvider jwtTokenProvider) {
        this.chatRoomService = chatRoomService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @ApiOperation(value = "/api/chat/createRoom", notes = "채팅방 만들기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "관리자에게 문의")
    })
    @PostMapping("/createRoom")
    public @ResponseBody
    Map<String, Object> createRoom(@RequestBody String name) {
        Map<String, Object> resultMap = new HashMap<>();
        ChatRoom chatRoom = chatRoomService.createChatRoom(name);
        resultMap.put("chatRoom", chatRoom);
        resultMap.put("success", true);
        return resultMap;
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        List<ChatRoom> chatRooms = chatRoomService.findAllRoom();
        chatRooms.stream().forEach(room -> room.setUserCount(chatRoomService.getUserCount(room.getRoomNo())));
        return chatRooms;
    }

    @ApiOperation(value = "/api/chat/room/enter/{roomNo}", notes = "해당 채팅방의 번호 가져오기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "관리자에게 문의")
    })
    @GetMapping("/room/enter/{roomNo}")
    public String getRoomNo(@PathVariable String roomNo) {
        return roomNo;
    }

    @ApiOperation(value = "/api/chat/room/{roomNo}", notes = "채팅방의 정보 가져오기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "관리자에게 문의")
    })
    @GetMapping("/room/{roomNo}")
    public ChatRoom getRoomInfo(@PathVariable String roomNo) {
        return chatRoomService.findRoomByRoomNo(roomNo);
    }

    @ApiOperation(value = "/api/chat/user", notes = "채팅방의 정보 가져오기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "관리자에게 문의")
    })
    @GetMapping("/user")
    public ChatRoomUserInfo getUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.LOGIN_MEMBER);
        return ChatRoomUserInfo.builder().username(user.getName())
                .accessToken(jwtTokenProvider.generateToken(user.toAuthentication()))
                .build();
    }
}
