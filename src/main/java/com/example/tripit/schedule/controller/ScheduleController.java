package com.example.tripit.schedule.controller;

import com.example.tripit.error.ErrorCode;
import com.example.tripit.schedule.dto.DetailScheduleDto;
import com.example.tripit.schedule.dto.ScheduleDto;
import com.example.tripit.schedule.dto.ScheduleRequest;
import com.example.tripit.schedule.service.ApiConnection;
import com.example.tripit.schedule.service.ScheduleService;
import com.example.tripit.user.dto.CustomUserDetails;
import com.example.tripit.user.repository.UserRepository;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/home")
public class ScheduleController {

    private final ApiConnection apiConnection; //외부 Api 연결 담당 서비스 클래스
    private final ScheduleService scheduleService; //일정 관련 비즈니스 로직을 담당 서비스 클래스
    private final UserRepository userRepository;


    private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    @Autowired
    public ScheduleController(ApiConnection apiConnection, ScheduleService scheduleService, UserRepository userRepository) {
        this.apiConnection = apiConnection;
        this.scheduleService = scheduleService;
        this.userRepository = userRepository;
    }

    @GetMapping({"/apiList/{metroId}/{pageNo}", "/apiList/{metroId}/{pageNo}/{contentTypeId}"})
    public ResponseEntity<Object> travelApi(@PathVariable String metroId,
                                            @PathVariable String pageNo,
                                            @PathVariable(required = false) String contentTypeId) {
        //contentTypeId가 제공되지 않았을 때 기본값 설정
        if (contentTypeId == null) {
            contentTypeId = "12";
        }
        System.out.println("????");
        //apiConnection 메서드 호출 및 반환
        return apiConnection.cultureFacilityApi(metroId, pageNo, contentTypeId);
    }

    @GetMapping("/apiDetail/{contentId}")
    public ResponseEntity<Object> apiTest2(@PathVariable String contentId) {
        return apiConnection.detailApi(contentId);
    }

    @GetMapping("/apiSearch/{metroId}/{pageNo}/{contentTypeId}/{keyword}")
    public ResponseEntity<Object> apiTest3(@PathVariable String metroId,
                                           @PathVariable String pageNo,
                                           @PathVariable String contentTypeId,
                                           @PathVariable String keyword) throws UnsupportedEncodingException {
        return apiConnection.searchApi(metroId, pageNo, contentTypeId, keyword);
    }

    @PostMapping("/saveSchedule")
    public ResponseEntity<?> saveSchedule(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody ScheduleRequest scheduleRequest) {
        //CustomUserDetails에서 userId 추출
        String email = customUserDetails.getUsername();
        long userId = userRepository.findUserIdByEmail(email);

        try {
            List<ScheduleDto> scheduleDtos = scheduleService.saveSchedule(scheduleRequest, userId);

            return ResponseEntity.ok(scheduleDtos);
        } catch (Exception e) {
            // 예외 로그 출력
            logger.error("일정 저장 실패: {}", e.getMessage(), e);

            // 클라이언트에게 전달할 에러 메시지
            String errorMessage = "일정 저장 실패: " + e.getMessage();
            //ErrorResponse result = new ErrorResponse(ErrorCode.SCHEDULE_FAIL);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }


//    @GetMapping("/{scheduleId}")
//    public ResponseEntity<?> getDetailSchedule(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long scheduleId) {
//        String email = customUserDetails.getUsername();
//        Integer userId = userRepository.findUserIdByEmail(email);
//        System.out.println(userId);
//        return scheduleService.detailSchedule(userId, scheduleId);
//    }



}
