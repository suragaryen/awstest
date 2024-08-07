package com.example.tripit.schedule.entity;

import com.example.tripit.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SCHEDULE")
public class ScheduleEntity {

    @Id //PK 값 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK 값 DB에서 자동 생성
    @Column(name = "schedule_id")
    private Long scheduleId;

    @Column(nullable = false, name = "metro_id")
    private String metroId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(nullable = false, name = "start_date")
    private LocalDate startDate;

    @Column(nullable = false, name = "end_date")
    private LocalDate endDate;

    @Column(nullable = false, name = "register_date")
    private LocalDate registerDate;

    @Column(nullable = false, name = "schedule_title")
    private String scheduleTitle;

}
