package com.example.tripit.user.controller;

import com.example.tripit.user.dto.CustomUserDetails;
import com.example.tripit.user.entity.UserEntity;
import com.example.tripit.user.repository.UserRepository;
import com.example.tripit.result.ResultCode;
import com.example.tripit.result.ResultResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {


    private final UserRepository userRepository;

    @Autowired
    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String mainP(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
    	
        String email = customUserDetails.getUsername();//email
        String role = customUserDetails.getAuthorities().iterator().next().getAuthority();
        long userId = userRepository.findUserIdByEmail(email);
    	
    	System.out.println(userId);
        return "main Controller";
    }


//    @GetMapping("/test")
//    @ResponseBody
//    public String test(HttpServletRequest request, HttpServletResponse response) {
//
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        System.out.println("test cont " + username);
//
//        return username;
//    }


    @GetMapping("/user")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails, HttpServletResponse response
    ) {

        String email = customUserDetails.getUsername();//email
        String role = customUserDetails.getAuthorities().iterator().next().getAuthority();
        long userId = userRepository.findUserIdByEmail(email);


       //토큰 살아있을 때 유저 정보
        ResultResponse result = ResultResponse.of(ResultCode.GET_MY_INFO_SUCCESS, email, userId, role);
        //토큰 만료 되었을 때
        ResultResponse resultError = ResultResponse.of(ResultCode.ACCESS_TOKEN_EXPIRED,email, userId, role);


        if (email != null) {
            System.out.println(email);
            return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
        }

        // email이 null이 아니면 이 라인에 도달하지 않음. 모든 다른 경우는 401 반환
        System.out.println(resultError.getStatus());
        return new ResponseEntity<>(resultError, HttpStatus.UNAUTHORIZED);

    }

}
