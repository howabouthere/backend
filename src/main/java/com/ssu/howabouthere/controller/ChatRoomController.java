package com.ssu.howabouthere.controller;

import com.ssu.howabouthere.helper.JwtTokenProvider;
import com.ssu.howabouthere.service.impl.ChatRoomServiceImpl;
import com.ssu.howabouthere.vo.ChatRoom;
import com.ssu.howabouthere.vo.ChatRoomUserInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ApiOperation(value = "/chat/createRoom", notes = "채팅방 만들기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "관리자에게 문의")
    })
    @PostMapping("/createRoom")
    public void createRoom(@RequestParam String name, Model model) {
        ChatRoom chatRoom = chatRoomService.createChatRoom(name);
        model.addAttribute("chatRoom", chatRoom);
        model.addAttribute("success", true);
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        List<ChatRoom> chatRooms = chatRoomService.findAllRoom();
        chatRooms.stream().forEach(room -> room.setUserCount(chatRoomService.getUserCount(room.getRoomNo())));
        return chatRooms;
    }

    @ApiOperation(value = "/chat/room/enter/{roomNo}", notes = "해당 채팅방의 번호 가져오기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "관리자에게 문의")
    })
    @GetMapping("/room/enter/{roomNo}")
    public String getRoomNo(@PathVariable String roomNo) {
        return roomNo;
    }

    @ApiOperation(value = "/chat/room/{roomNo}", notes = "채팅방의 정보 가져오기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "관리자에게 문의")
    })
    @GetMapping("/room/{roomNo}")
    public ChatRoom getRoomInfo(@PathVariable String roomNo) {
        return chatRoomService.findRoomByRoomNo(roomNo);
    }

    @ApiOperation(value = "/user", notes = "채팅방의 정보 가져오기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "관리자에게 문의")
    })
    @GetMapping("/user")
    public ChatRoomUserInfo getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return ChatRoomUserInfo.builder().username(username).token(jwtTokenProvider.generateToken(username)).build();
    }
}
