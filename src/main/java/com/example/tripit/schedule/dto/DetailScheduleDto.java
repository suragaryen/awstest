package com.example.tripit.schedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DetailScheduleDto {

    @JsonProperty("schedule_detail_id")
    private Long scheduleDetailId;

    @JsonProperty("schedule_id")
    private Long scheduleId;

    @JsonProperty("schedule_order")
    private int scheduleOrder;

    @JsonProperty("start_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime startTime;

    @JsonProperty("end_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime endTime;

    @JsonProperty("content_id")
    private String contentId;

    @JsonProperty("register_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime registerTime;

}
