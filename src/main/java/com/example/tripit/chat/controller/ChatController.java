package com.example.tripit.chat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.tripit.chat.dto.ChatRoomDto;
import com.example.tripit.chat.entity.ChatMessage;
import com.example.tripit.chat.entity.ChatRoom;
import com.example.tripit.chat.service.ChatJoinService;
import com.example.tripit.chat.service.ChatMessageService;
import com.example.tripit.chat.service.ChatRoomService;
import com.example.tripit.user.dto.CustomUserDetails;
import com.example.tripit.user.repository.UserRepository;
@Controller
@RequestMapping("/chat")
@RestController
//http://localhost:8080/chat/createChatRoom
public class ChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private ChatRoomService chatRoomService;
    
    @Autowired
    private ChatJoinService chatJoinService;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    public ChatController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }
    
    // HTTP GET 방식으로 채팅방 생성화면 보여주기
    @GetMapping("/createChatRoom")
    public ModelAndView createChatRoom() {
        ModelAndView mav = new ModelAndView();
        List<ChatRoom> chatRooms = chatRoomService.getAllChatRooms();
        // 만들어진 채팅방 확인
        mav.addObject("chatRooms", chatRooms);
        System.out.println("Chat Rooms: " + chatRooms); // 콘솔에서 확인
        mav.setViewName("chat/createChatRoom");
        return mav;
    }
    
    // 현재 만들어진 채팅방 리스트 호출
    @GetMapping("/chatRooms")
    @ResponseBody // JSON 형식으로 반환하기 위한 어노테이션
    public List<ChatRoom> getAllChatRooms() {
        List<ChatRoom> chatRooms = chatRoomService.getAllChatRooms();
//        System.out.println("Chat Rooms: " + chatRooms); // 콘솔에서 확인
        return chatRooms;
    }

    
    
    // 채팅방 만들기	
//    @PostMapping(value = "/createChatRoom", consumes = "application/json", produces = "application/json")
//    public ResponseEntity<?> createChatRoom(@RequestBody ChatRoomDto chatRoomDto,
//                                            @AuthenticationPrincipal CustomUserDetails customUserDetails
//    										) {
//    	// 유저정보 시큐리티 확인
//    	String email = customUserDetails.getUsername();//email
//        String role = customUserDetails.getAuthorities().iterator().next().getAuthority();
//        Integer userId = userRepository.findUserIdByEmail(email);
//
//        System.out.println(userId);
//
//    	 // postId로 이미 존재하는 채팅방이 있는지 확인
//        ChatRoom existingChatRoom = chatRoomService.findByPostId(chatRoomDto.getPostId());
//        if (existingChatRoom != null) {
//            // 이미 존재하는 채팅방이면 클라이언트에게 메시지 반환
//            return ResponseEntity.badRequest().body("채팅방이 존재합니다.");
//        }
//
//
//        // 채팅방 생성
//        ChatRoom chatRoom = chatRoomService.createChatRoom(chatRoomDto.getChatroomName(),
//                                                           chatRoomDto.getPostId());
//
//
//        // 채팅방에 입장하는 URL 생성 (예시로 http://localhost:8080/chat/joinChatRoom/{roomId})
////        String enterChatRoomUrl = "http://localhost:8080/chat/joinChatRoom/" + chatRoom.getRoomId();
//
//
////
////        // 세션에서 로그인된 사용자 정보 가져오기
////        User loggedInUser = (User) session.getAttribute("loggedInUser");
////
////        if (loggedInUser != null) {
////            // 로그인된 사용자의 ID를 이용하여 ChatJoin 테이블에 삽입
////            int userId = loggedInUser.getUserId();
////            chatJoinService.joinRoom(userId, chatRoom.getRoomId());
////        } else {
////            // 로그인되지 않은 사용자는 401 Unauthorized 응답 반환
////            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
////        }
//
//        // 모든 사용자 목록 가져오기
////        List<User> users = userService.getAllUsers();
//
//        // 응답 객체 생성
//        Map<String, Object> response = new HashMap<>();
////        response.put("users", users);
//        response.put("chatRoom", chatRoom);
//
//        return ResponseEntity.ok(response);
//    }

    
//    @GetMapping("/joinChatRoom/{roomId}")
//    public ResponseEntity<?> joinRoom(HttpSession session, @PathVariable("roomId") int roomId) {
//        // 세션에서 로그인된 사용자 정보 가져오기
//        User loggedInUser = (User) session.getAttribute("s_id");
//        if (loggedInUser != null) {
//            // 로그인된 사용자의 ID를 이용하여 방에 참여
//            int userId = loggedInUser.getUserId(); // 예시로 getUserId() 메서드를 가정
//            // 방 참여 서비스 호출
//            chatJoinService.joinRoom(userId, roomId);
//
//            // 생성된 채팅방 정보를 JSON 형식으로 반환
//            Map<String, Object> response = new HashMap<>();
//            response.put("message", "채팅방에 입장하였습니다.");
//            response.put("roomId", roomId);
//
//            return ResponseEntity.ok(response);
//        } else {
//            // 로그인되지 않은 사용자는 로그인 페이지로 리다이렉트 또는 에러 처리
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
//        }
//    }



    // WebSocket을 이용한 메시지 송신 처리
    @MessageMapping("/send/message")
    @SendTo("/topic/public")
    public ChatMessage broadcastMessage(ChatMessage message) {
        return chatMessageService.saveChatMessage(message);
    }

    // AJAX를 이용한 메시지 송신 처리
    @PostMapping("/ajax/send")
    public void sendMessageViaAjax(@RequestBody ChatMessage message) {
        ChatMessage savedMessage = chatMessageService.saveChatMessage(message);
        messagingTemplate.convertAndSend("/topic/public", savedMessage);
    }
    
}
