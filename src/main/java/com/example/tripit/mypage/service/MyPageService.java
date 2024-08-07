package com.example.tripit.mypage.service;

import com.example.tripit.community.dto.CommunityDTO;
import com.example.tripit.community.dto.MetroENUM;
import com.example.tripit.community.dto.PostDTO;
import com.example.tripit.community.entity.PostEntity;
import com.example.tripit.community.repository.PostRepository;
import com.example.tripit.community.service.CommunityService;
import com.example.tripit.schedule.dto.DetailScheduleDto;
import com.example.tripit.schedule.dto.ScheduleDto;
import com.example.tripit.schedule.entity.DetailScheduleEntity;
import com.example.tripit.schedule.entity.ScheduleEntity;
import com.example.tripit.schedule.repository.DetailScheduleRepository;
import com.example.tripit.schedule.repository.ScheduleRepository;
import com.example.tripit.user.dto.UserDTO;
import com.example.tripit.user.entity.UserEntity;
import com.example.tripit.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MyPageService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private DetailScheduleRepository detailScheduleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommunityService communityService;

    public MyPageService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    //userId 엔티티 찾기
    public Optional<UserDTO> getUserDTOById(Long userId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        return userEntityOptional.map(this::mapToUserDTO);
    }

    //user엔티티를 DTO로 변환하기
    private UserDTO mapToUserDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setNickname(userEntity.getNickname()); //닉네임
        String userPic = userEntity.getUserpic();
        String intro = userEntity.getIntro();

        if (userPic == null) {
            userPic = "";
        }

        if (intro == null) {
            intro = "";
        }

        userDTO.setUserIntro(intro); //자기소개
        userDTO.setUserpic(userPic); //사진
        userDTO.setGender(userEntity.getGender()); //성별
        userDTO.setEmail(userEntity.getEmail()); //이메일
        userDTO.setPassword(userEntity.getPassword()); //비밀번호
        userDTO.setUsername(userEntity.getUsername()); //이름
        userDTO.setBirth(userEntity.getBirth());

        return userDTO;
    }

    //프로필 수정
    @Transactional
    public UserDTO profileUpdate(UserDTO userDTO, Long userId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();

            //엔티티의 필드 업데이트
            userEntity.setNickname(userDTO.getNickname());
            userEntity.setIntro(userDTO.getUserIntro());
            userEntity.setUserpic(userDTO.getUserpic());

            //Dirty Checking

            //엔티티를 DTO로 변환하여 반환
            return mapToUserDTO(userEntity);
        } else {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
    }

    //개인정보 수정
    @Transactional
    public UserDTO personalUpdate(UserDTO userDTO, Long userId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();

            //엔티티의 필드 업데이트
            //userEntity.setPassword(userDTO.getPassword());
            userEntity.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

            //Dirty Checking

            //엔티티를 DTO로 변환하여 반환
            return mapToUserDTO(userEntity);
        } else {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
    }



    //전체 일정 목록
    public List<ScheduleDto> findScheduleList(Long userId) {
        List<ScheduleEntity> scheduleEntities = scheduleRepository.findByUserId(userId);

        return scheduleEntities.stream()
                .map(scheduleEntity -> modelMapper.map(scheduleEntity, ScheduleDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ScheduleDto> schedulesDelete(List<Long> scheduleIds, Long userId) {
        List<ScheduleEntity> schedules = scheduleRepository.findAllById(scheduleIds);

        if (schedules.size() != scheduleIds.size()) {
            throw new IllegalArgumentException("일정을 찾을 수 없음");
        }

        if (scheduleIds.size() == 1) {
            Long scheduleId = scheduleIds.get(0);
            scheduleRepository.deleteById(scheduleId);
        }

        scheduleRepository.deleteAllById(scheduleIds);
        List<ScheduleEntity> scheduleEntities = scheduleRepository.findByUserId(userId);

        return scheduleEntities.stream()
                .map(scheduleEntity -> modelMapper.map(scheduleEntity, ScheduleDto.class))
                .collect(Collectors.toList());
    }


    //상세 일정
    public List<DetailScheduleDto> detailSchedule(Long scheduleId) {
        // DetailScheduleEntity 조회
        List<DetailScheduleEntity> detailSchedulesEntities = detailScheduleRepository.findByScheduleId(scheduleId);
        System.out.println(detailSchedulesEntities); // 콘솔 출력
        // 조회된 데이터 반환
        return detailSchedulesEntities.stream()
                .map(detailScheduleEntity -> modelMapper.map(detailScheduleEntity, DetailScheduleDto.class))
                .collect(Collectors.toList());
    }

    public void scheduleDelete(Long scheduleId) {
        Optional<ScheduleEntity> scheduleEntity = scheduleRepository.findById(scheduleId);
        if (scheduleEntity.isEmpty()) {
            throw new IllegalArgumentException("일정을 찾을 수 없음");
        }
        scheduleRepository.deleteById(scheduleId);
    }

    public List<CommunityDTO> postList(Long userId) {
        UserEntity user = new UserEntity();
        user.setUserId(userId);

        List<PostEntity> postEntities = postRepository.findByUserId(user);

        return postEntities.stream()
                .map(post -> {
                    UserEntity userEntity = post.getUserId();
                    ScheduleEntity schedule = post.getScheduleId();

                    return new CommunityDTO(
                            post.getPostId(),
                            post.getPostTitle(),
                            post.getPostContent(),
                            post.getPersonnel(),
                            post.getViewCount(),
                            post.getExposureStatus(),
                            post.getPostPic(),
                            post.getPostDate(),
                            user.getUserId(),
                            user.getNickname(),
                            user.getGender(),
                            user.getBirth(),
                            user.getUserpic(),
                            schedule.getScheduleId(),
                            schedule.getMetroId(),
                            MetroENUM.getNameById(schedule.getMetroId()),
                            schedule.getStartDate(),
                            schedule.getEndDate()
                    );
                })
                .collect(Collectors.toList());
    }

//    public List<CommunityDTO> postDetail(Long userId, Long postId) {
//        return communityService.loadCommunityList(userId, postId);
//    }

    public void postDelete(Long userId, Long postId) {

        Optional<PostEntity> postEntity = postRepository.findById(postId);
        //System.out.println(postEntity + "d");
        if (postEntity.isEmpty()) {
            throw new IllegalArgumentException("글을 찾을 수 없음");
        }
        postRepository.deleteById(postId);
        postList(userId);
    }

}
