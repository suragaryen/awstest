package com.example.tripit.schedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.time.LocalDate;


@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {

    @JsonProperty("schedule_id")
    private Long scheduleId;

    @JsonProperty("metro_id")
    private String metroId;

    @JsonProperty("user_id")
    private long userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    @JsonProperty("end_date")
    private LocalDate endDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    @JsonProperty("register_date")
    private LocalDate registerDate;

    @JsonProperty("schedule_title")
    private String scheduleTitle;

}
