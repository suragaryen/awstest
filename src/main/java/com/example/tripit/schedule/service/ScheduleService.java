package com.example.tripit.schedule.service;

import com.example.tripit.schedule.dto.DetailScheduleDto;
import com.example.tripit.schedule.dto.ScheduleDto;
import com.example.tripit.schedule.dto.ScheduleRequest;
import com.example.tripit.schedule.entity.DetailScheduleEntity;
import com.example.tripit.schedule.entity.ScheduleEntity;
import com.example.tripit.schedule.repository.DetailScheduleRepository;
import com.example.tripit.schedule.repository.ScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private DetailScheduleRepository detailScheduleRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ScheduleDto> saveSchedule(ScheduleRequest scheduleRequest, long userId) throws Exception {
        ScheduleDto scheduleDto = scheduleRequest.getScheduleDto();
        scheduleDto.setUserId(userId);
        try {
            //ModelMapper를 사용하여 DTO를 엔티티로 변환
            ScheduleEntity scheduleEntity = modelMapper.map(scheduleDto, ScheduleEntity.class);
            scheduleEntity.setRegisterDate(LocalDate.now());
            //엔티티 DB 저장
            scheduleRepository.save(scheduleEntity);
            Long scheduleId = scheduleEntity.getScheduleId();

            System.out.println(userId);

            return saveDetailSchedule(scheduleId, userId, scheduleRequest);

        } catch (Exception e) {

            throw new Exception("일정 저장 실패", e);

        }
    }

    public List<ScheduleDto> saveDetailSchedule(Long scheduleId, long userId, ScheduleRequest scheduleRequest) {
        List<DetailScheduleDto> detailScheduleDtoList = scheduleRequest.getDetailScheduleDto();

        for (DetailScheduleDto detailScheduleDto : detailScheduleDtoList) {
            DetailScheduleEntity detailScheduleEntity = modelMapper.map(detailScheduleDto, DetailScheduleEntity.class);
            detailScheduleEntity.setRegisterTime(LocalDateTime.now());
            detailScheduleEntity.setScheduleId(scheduleId);
            //엔티티 DB 저장
            detailScheduleRepository.save(detailScheduleEntity);
        }
        List<ScheduleEntity> scheduleEntities = scheduleRepository.findByUserId(userId);

        //System.out.println(scheduleEntities);

        return scheduleEntities.stream()
                .map(scheduleEntity -> modelMapper.map(scheduleEntity, ScheduleDto.class))
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> detailSchedule(long userId, Long scheduleId) {
        // 1. ScheduleEntity 조회
        Optional<ScheduleEntity> scheduleEntityOpt = scheduleRepository.findByUserIdAndScheduleId(userId, scheduleId);
        if (scheduleEntityOpt.isEmpty()) {
            // ScheduleEntity가 없을 경우 처리
            return ResponseEntity.notFound().build();
        }

        // 2. DetailScheduleEntity 조회
        List<DetailScheduleEntity> detailSchedules = detailScheduleRepository.findByScheduleId(scheduleId);

        // 조회된 데이터 반환
        return ResponseEntity.ok(detailSchedules);
    }

}
